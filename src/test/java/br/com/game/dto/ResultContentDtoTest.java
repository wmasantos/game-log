package br.com.game.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultContentDtoTest {

    private static String MESSAGE = "Jogo encontrado";
    private static int HTTP_CODE = 200;
    private static int ERROR_CODE = 0;

    @Test
    public void testMainMethods(){
        assertThat(ResultContentDto.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
                hasValidBeanEquals(), hasValidBeanToString(), hasValidBeanHashCode()));
    }

    @Test
    public void testAttributeValues(){
        ResultContentDto<GameStatusDto> resultContentDto = getResultContentDto();
        assertEquals(MESSAGE, resultContentDto.getMessage());
        assertEquals(HTTP_CODE, resultContentDto.getHttpCode());
        assertEquals(ERROR_CODE, resultContentDto.getCode());
    }

    private ResultContentDto<GameStatusDto> getResultContentDto() {
        return ResultContentDto
                .<GameStatusDto>builder()
                .code(0)
                .httpCode(200)
                .message("Jogo encontrado")
                .content(GameStatusDtoTest.getGameStatusDto())
                .build();
    }
}