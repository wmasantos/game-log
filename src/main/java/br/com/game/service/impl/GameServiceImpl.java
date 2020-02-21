package br.com.game.service.impl;

import br.com.game.dto.GameStatusDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface GameServiceImpl {
    File loadData(MultipartFile multipartFile) throws IOException;
    List<GameStatusDto> gameBuild(String fileName) throws IOException;
    GameStatusDto getGameById(Integer gameId) throws IOException;
}
