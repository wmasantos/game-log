package br.com.game.resource;

import br.com.game.service.GameService;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameResourceTests {

    private MockMvc mockMvc;

    @Autowired
    private GameResource gameResource;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameResource).build();
    }

    @Test
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

    public byte[] loadLogMinified() throws IOException {
        File file = new File("gameMinified.log");
        return Files.toByteArray(file);
    }
}
