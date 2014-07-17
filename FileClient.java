import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    static DataInputStream din = null;
    static DataOutputStream dout = null;
    static Socket s = null;

    public static void main(String[] args) {
        try {
            s = new Socket("127.0.0.1", 2224);

            File file = new File("d:/05.jpg"); // 定义文件
            FileInputStream fis = new FileInputStream(file); // 定义文件输入流
            din = new DataInputStream(new BufferedInputStream(fis)); // 用缓存流包装文件输入流（提高读取速度），然后再包装成数据输入流
            dout = new DataOutputStream(s.getOutputStream());// 定义数据输出流

            dout.writeUTF(String.valueOf(file.length())); // 发送文件长度

            byte[] buffer = new byte[1024]; // 定义缓存
            int len = 0;
            while ((len = din.read(buffer)) != -1) {
                dout.write(buffer, 0, len); // 向服务器发送数据
            }
            dout.flush();

        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (din != null) {
                    din.close();
                    din = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (dout != null) { // 最后一定要关闭输出流，不然数据发送不出去。导致一直连接着，不断开
                    dout.close();
                    dout = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (s != null) {
                    s.close();
                    s = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
