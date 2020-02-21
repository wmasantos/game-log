package br.com.game.resource;

import br.com.game.dto.GameStatusDto;
import br.com.game.dto.ResultContentArrayDto;
import br.com.game.dto.ResultContentDto;
import br.com.game.dto.ResultSimpleDto;
import br.com.game.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @ApiOperation(value = "Lista todos os logs. Método da V1")
    @GetMapping("/v1/log")
    public ResponseEntity<ResultContentArrayDto<GameStatusDto>> getV1() throws IOException {

        List<GameStatusDto> gameStatusDtoList = gameService.gameBuild(GameService.FILE_NAME);

        return ResponseEntity.ok(
                ResultContentArrayDto
                        .<GameStatusDto>builder()
                        .code(0)
                        .httpCode(200)
                        .message("Logs listados com sucesso")
                        .content(gameStatusDtoList)
                        .build()
        );
    }

    @ApiOperation(value = "Detalha um jogo informando o numero do jogo como parametro no path. Método da V1")
    @GetMapping("/v1/log/{game}")
    public ResponseEntity<ResultContentDto<GameStatusDto>>getByGameV1(@PathVariable("game") Integer game) throws IOException {

        GameStatusDto gameStatusDto = gameService.getGameById(game);

        if(gameStatusDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResultContentDto
                            .<GameStatusDto>builder()
                            .code(1)
                            .httpCode(404)
                            .message("Nenhuma partida encontrada")
                            .build()
                    );

        } else {
            return ResponseEntity.ok(
                    ResultContentDto
                            .<GameStatusDto>builder()
                            .code(0)
                            .httpCode(200)
                            .message("Partida encontrada")
                            .content(gameStatusDto)
                            .build()
            );
        }
    }
}
