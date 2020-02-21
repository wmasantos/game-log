package br.com.game.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collections;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultContentArrayDtoTest {

    private static String MESSAGE = "Jogos listados com sucesso";
    private static int HTTP_CODE = 200;
    private static int ERROR_CODE = 0;
    private static int LIST_SIZE = 1;

    @Test
    public void testMainMethods(){
        assertThat(ResultContentArrayDto.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
                hasValidBeanToString()));
    }

    @Test
    public void testAttributeValues(){
        ResultContentArrayDto<GameStatusDto> resultContentDto = getResultContentArrayDto();
        assertEquals(MESSAGE, resultContentDto.getMessage());
        assertEquals(HTTP_CODE, resultContentDto.getHttpCode());
        assertEquals(LIST_SIZE, resultContentDto.getContent().size());
    }

    private ResultContentArrayDto<GameStatusDto> getResultContentArrayDto() {
        return ResultContentArrayDto
                .<GameStatusDto>builder()
                .code(0)
                .httpCode(200)
                .message("Jogos listados com sucesso")
                .content(Collections.singletonList(GameStatusDtoTest.getGameStatusDto()))
                .build();
    }
}