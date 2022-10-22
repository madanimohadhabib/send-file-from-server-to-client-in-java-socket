import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {
        try(
                ServerSocket serverSocket = new ServerSocket(5000)){
                System.out.println("Serveur en attente");
                Socket clientSocket = serverSocket.accept();
                String str="",str2="";  

            while(!str.equals("Fin")){  

                System.out.println("Ecoute du port:5000");
                System.out.println(clientSocket+" connecté.");
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                str=dataInputStream.readUTF();  
                System.out.println("client dit que: "+str);  

                File f = new File("/FolderFile/Server/"+str);

                if(f.exists()) {
                     dataOutputStream.writeUTF(str);  
                     Envoyerfichier("/FolderFile/Server/"+str);
                }else {
                     dataOutputStream.writeUTF("non");  
            }
                dataOutputStream.flush();
          }
            dataInputStream.close();
            dataOutputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e){
             System.out.println("Attendre..");  

            e.printStackTrace();
        }
    }

     private static void Envoyerfichier(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        
  
        dataOutputStream.writeLong(file.length());  
  
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
   
}