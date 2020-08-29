package com.company;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class Main
{
    public static void main(String args[]) throws IOException
    {
        StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
        try
        {
            String sample = "감사헌금";
            Charset charset1 = Charset.forName("EUC-KR");
            ByteBuffer bb = charset1.encode(sample);
            byte[] ba = bb.array();


            File file=new File("/Users/jlee262/Documents/church/src/main/java/com/company/20200607.IN");    //creates a new file instance
            //FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"EUC-KR"));
            //BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream

            String line;
            int j = 0;
            while((line=br.readLine())!=null)
            {
                char[] chars = line.toCharArray();
                Charset charset = Charset.forName("EUC-KR");
                ByteBuffer byteBuffer = charset.encode(CharBuffer.wrap(chars));
                byte[] bytes = byteBuffer.array();

                System.out.println(new String(bytes));

                int[] ch = new int[chars.length];
                for (int i = 0; i < bytes.length; i++) {
                    char c = (char) bytes[i];
                    if (j > 6)
                    {
                        if (c == 0) break;
                        if (c < ' ' || c == '}' || c == '{') continue;

                        if (c == '?') {
                            /*
                            for (int k = 0; k < ba.length; k++) {
                                bytes[i++] = ba[k];
                            }
                             */
                            System.out.println();
                        }

                        if (c <= 0x7F && c != '?')
                        {
                            bytes[i] = (byte) (0x7F + 0x20 - bytes[i]);
                            c = (char) bytes[i];
                        }
                        else if (c > 0x7F || c == '?') {
                            /*
                            String s = Integer.toBinaryString(chars[i]);
                            if (s.length() < 16) {
                                s = "0" + s;
                            }
                            String s1 = s.substring(0, 8);
                            String s2 = s.substring(8);
                            String n = s2 + s1;
                            int ni = Integer.parseInt(n, 2);
                            c = (char) ni;
                             */
                            if (c == '?') {
                                int q = 0;
                            }
                            byte temp = bytes[i];
                            bytes[i] = bytes[++i];
                            bytes[i] = temp;
                        }
                    }
                    else {
                    }

                    //System.out.print(c);

                }

                CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(bytes));
                char[] chs = charBuffer.array();
                System.out.println(line);
                System.out.println(" &&&& " + new String(chs).trim());

                j++;

                System.out.println();

                sb.append(line);      //appends line to string buffer
                sb.append("\n");     //line feed
            }
            //fr.close();    //closes the stream and release the resources
            System.out.println(sb.toString());   //returns a string that textually represents the object
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        OutputStream outputStream = new FileOutputStream("/Users/jlee262/Documents/church/src/main/java/com/company/OUT.INI");
        Writer       writer       = new OutputStreamWriter(outputStream,
            Charset.forName("UTF-8"));

        try
        {
            writer.write(sb.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        writer.close();
    }
}