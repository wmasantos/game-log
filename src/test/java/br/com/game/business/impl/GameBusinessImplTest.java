package br.com.game.business.impl;

import br.com.game.business.impl.GameBusinessImpl;
import br.com.game.dto.GameStatusDto;
import com.google.common.io.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameBusinessImplTest {
    @Autowired
    private GameBusinessImpl gameBusinessImpl;

    @Test
    public void parseLog() throws Exception {
        assertFalse(gameBusinessImpl.gameBuild("gameMinified.log").isEmpty());
    }

    @Test(expected = IOException.class)
    public void parseLogWithIncoorrectFile() throws IOException{
        gameBusinessImpl.gameBuild("error.log");
    }

    @Test
    public void testLoadData() throws Exception {
        MockMultipartFile gameLog = new MockMultipartFile("file", "game.log",
                "text/plain", loadLogMinified());

        File file = gameBusinessImpl.loadData(gameLog);

        assertTrue("game.log existe?", file.exists());
    }

    @Test
    public void testGetGameById() throws Exception {
        MockMultipartFile gameLog = new MockMultipartFile("file", "game.log",
                "text/plain", loadLogMinified());

        File file = gameBusinessImpl.loadData(gameLog);

        GameStatusDto gameStatusDto = gameBusinessImpl.getGameById(1);

        assertTrue("game.log existe?", file.exists());
        assertNotNull("Um jogo foi encontrado?", gameStatusDto);
        assertEquals("game_1 existe?", "game_1", gameStatusDto.getGameName());
    }

    public byte[] loadLogMinified() throws IOException {
        File file = new File("gameMinified.log");
        return Files.toByteArray(file);
    }
}