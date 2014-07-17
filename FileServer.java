import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static DataInputStream din = null;
    static DataOutputStream dout = null;
    static Socket s = null;

    public static void main(String[] args) {
        new Thread() { // 开启子线程
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(2224); // 这个必须在while外，不然会循环连接端口，出错
                    while (true) {
                        System.out.println("--------等待用户连接--------------");
                        s = ss.accept();
                        System.out.println("--------用户连接上了--------------");

                        din = new DataInputStream(new BufferedInputStream(s
                                .getInputStream()));// 使用缓存进行包装，提示读取速度
                        System.out.println("文件长度:" + din.readUTF()); // 显示接收文件长度

                        File file = new File("d:/01.jpg");
                        FileOutputStream fos = new FileOutputStream(file);
                        dout = new DataOutputStream(new BufferedOutputStream(
                                fos));

                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = din.read(buffer)) != -1) {
                            dout.write(buffer, 0, len);
                        }
                        dout.flush();
                        dout.close(); // 下面的finally要等到循环结束后才执行，如果不执行close，文件无法正常打开

                        System.out.println("接受成功");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally { // 等while循环结束后才会执行，
                    try {
                        if (dout != null) {
                            dout.close();
                            dout = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (din != null) {
                            din.close();
                            din = null;
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
        }.start();
    }
}
