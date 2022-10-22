import java.io.*;
import java.net.Socket;

public class Client {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost",5000)) {
            System.out.println("Connexion");
            String str="",str2=""; 
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while(!str.equals("Fin")){  
    
                str=br.readLine();  
                dataOutputStream.writeUTF(str);  
                dataOutputStream.flush();  
                str2=dataInputStream.readUTF();  
                    int index = str2.lastIndexOf('.');
                if(index > 0) {
                  String extension = str2.substring(index + 1);
                   String  fileName = str2.replaceFirst("[.][^.]+$", "");

                        if(str2.equals(str)){
                            System.out.println("serveur dit : fichier existe "+"("+str2+")");  
                            System.out.println("\nle fichier est déplacé dans un dossier Client ");
                            recevoirfichier("/FolderFile/Client/"+fileName+"-copie"+"."+extension);

                        }}
                        else if(str2.equals("non")){
                            System.out.println("serveur : le fichier n’existe pas "+str);  

                        }
}
            dataInputStream.close();
            dataInputStream.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

  private static void recevoirfichier(String fileName) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size = dataInputStream.readLong();     
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;     
        }
        fileOutputStream.close();
    }
}