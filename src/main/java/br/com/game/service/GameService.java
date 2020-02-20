package br.com.game.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class GameService {
    private static final String FILE_NAME = "game.log";

    public File loadData(MultipartFile multipartFile) throws IOException{

        File file = new File(FILE_NAME);

        if(file.exists())
            file.delete();

        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        return file;
    }
}
