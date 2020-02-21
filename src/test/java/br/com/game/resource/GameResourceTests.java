package br.com.game.resource;

import br.com.game.service.GameService;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameResourceTests {

    private MockMvc mockMvc;

    @Autowired
    private GameResource gameResource;

    @Autowired
    private GameService gameService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameResource).build();
    }

    @Test
    @Order(0)
    public void uploadFileLog() throws Exception{
        MockMultipartFile gameLog = new MockMultipartFile("file", "game.log",
                "text/plain", loadLogMinified());

        this.mockMvc.perform(MockMvcRequestBuilders
                .multipart("/game/uploadLog")
                .file(gameLog))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void callMissingExpectedParam() throws Exception{
        MockMultipartFile gameLog = new MockMultipartFile("anotherParam", "game",
                "video/plain", loadLogMinified());

        this.mockMvc.perform(MockMvcRequestBuilders
                .multipart("/game/uploadLog")
                .file(gameLog))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getAllLogsV1() throws Exception {
        checkLogExist();

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/game/v1/log"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getGameByNameV1() throws Exception {
        checkLogExist();

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/game/v1/log/3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void gameNotFoundV1() throws Exception {
        checkLogExist();

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/game/v1/log/300"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    public byte[] loadLogMinified() throws IOException {
        File file = new File("gameMinified.log");
        return Files.toByteArray(file);
    }

    public boolean checkLogExist() throws IOException {
        File file = new File(GameService.FILE_NAME);

        if (!file.exists()) {
            MockMultipartFile gameLog = new MockMultipartFile("file", "game.log",
                    "text/plain", loadLogMinified());

            File fileResult = gameService.loadData(gameLog);

            return fileResult.exists();
        }

        return true;
    }
}
