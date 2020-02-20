package br.com.game.resource;

import br.com.game.dto.ResultSimpleDto;
import br.com.game.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/game")
@Api(value = "Game Controller")
public class GameResource {

    private GameService gameService;

    @Autowired
    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "Upload do arquivo de log")
    @PostMapping(value = "/uploadLog")
    public ResponseEntity<ResultSimpleDto> uploadLog(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        File file = gameService.loadData(multipartFile);

        if(file != null)
            return ResponseEntity.ok(new ResultSimpleDto(0, 200,
                    "Upload realizado com sucesso, consulta disponível"));
        else
            return ResponseEntity.badRequest().body(new ResultSimpleDto(1, 400, "Falha no upload"));
    }
}
