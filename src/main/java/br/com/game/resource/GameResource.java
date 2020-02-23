package br.com.game.resource;

import br.com.game.dto.GameStatusDto;
import br.com.game.dto.ResultContentArrayDto;
import br.com.game.dto.ResultContentDto;
import br.com.game.dto.ResultSimpleDto;
import br.com.game.business.GameBusiness;
import br.com.game.business.impl.GameBusinessImpl;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/game")
@Api(value = "Game Controller")
public class GameResource {

    private GameBusinessImpl gameBusiness;

    @Autowired
    public GameResource(GameBusinessImpl gameBusiness) {
        this.gameBusiness = gameBusiness;
    }

    @ApiOperation(value = "Upload do arquivo de log")
    @PostMapping(value = "/uploadLog")
    public ResponseEntity<ResultSimpleDto> uploadLog(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        gameBusiness.loadData(multipartFile);

        return ResponseEntity.ok(new ResultSimpleDto(0, 200,
                "Upload realizado com sucesso, consulta disponível"));

    }

    @ApiOperation(value = "Lista todos os logs. Método da V1")
    @GetMapping("/v1/log")
    public ResponseEntity<ResultContentArrayDto<GameStatusDto>> getV1() throws IOException {

        List<GameStatusDto> gameStatusDtoList = gameBusiness.gameBuild(GameBusiness.FILE_NAME);

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

        GameStatusDto gameStatusDto = gameBusiness.getGameById(game);

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

    @ApiOperation(value = "Lista todos os logs. Método da V2")
    @GetMapping("/v2/log")
    public ResponseEntity<HashMap<String, GameStatusDto>> getV2() throws IOException {

        HashMap<String, GameStatusDto> resultContent = gameBusiness.getWithHash(GameBusiness.FILE_NAME);

        return ResponseEntity.ok(resultContent);
    }

    @ApiOperation(value = "Detalha um jogo informando o numero do jogo como parametro no path. Método da V2")
    @GetMapping("/v2/log/{game}")
    public ResponseEntity<HashMap<String, GameStatusDto>> getByGameV2(@PathVariable("game") Integer game) throws IOException{

        GameStatusDto gameStatusDto = gameBusiness.getGameById(game);

        if(gameStatusDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            HashMap<String, GameStatusDto> map = new HashMap<>();
            map.put(gameStatusDto.getGameName(), gameStatusDto);

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
