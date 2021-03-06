package br.com.game.business;

import br.com.game.dto.GameStatusDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface GameBusiness {
    File loadData(MultipartFile multipartFile) throws IOException;
    List<GameStatusDto> gameBuild(String fileName) throws IOException;
    GameStatusDto getGameById(Integer gameId) throws IOException;
    HashMap<String, GameStatusDto> getWithHash(String file) throws IOException;
}
