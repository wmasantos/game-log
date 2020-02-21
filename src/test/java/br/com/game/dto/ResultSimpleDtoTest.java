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
public class ResultSimpleDtoTest {
    private static String MESSAGE = "Uploda realizado com sucesso";
    private static int HTTP_CODE = 200;
    private static int ERROR_CODE = 0;

    @Test
    public void testMainMethods(){
        assertThat(ResultSimpleDto.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
                hasValidBeanEquals(), hasValidBeanToString(), hasValidBeanHashCode()));
    }

    @Test
    public void testAttributeValues(){
        ResultSimpleDto resultSimpleDto = getSimpleDto();
        assertEquals(MESSAGE, resultSimpleDto.getMessage());
        assertEquals(HTTP_CODE, resultSimpleDto.getHttpCode());
        assertEquals(ERROR_CODE, resultSimpleDto.getCode());
    }

    private ResultSimpleDto getSimpleDto() {
        return ResultSimpleDto
                .builder()
                .code(0)
                .httpCode(200)
                .message("Uploda realizado com sucesso")
                .build();
    }
}