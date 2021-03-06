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
public class ExceptionDtoTest {

    private static String DEVELOPER_MESSAGE = "Jogo não encontrado";
    private static int HTTP_CODE = 404;
    private static int ERROR_CODE = 10;

    @Test
    public void testMainMethods(){
        assertThat(ExceptionDto.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
                hasValidBeanEquals(), hasValidBeanToString(), hasValidBeanHashCode()));
    }

    @Test
    public void testAttributeValues(){
        ExceptionDto exceptionDto = getException();
        assertEquals(DEVELOPER_MESSAGE, exceptionDto.getDeveloperMessage());
        assertEquals(HTTP_CODE, exceptionDto.getHttpCode());
        assertEquals(ERROR_CODE, exceptionDto.getErrorCode());
    }

    private ExceptionDto getException() {
        return ExceptionDto
                .builder()
                .httpCode(404)
                .developerMessage("Jogo não encontrado")
                .errorCode(10)
                .exception(new Exception("Game Not Found"))
                .build();
    }
}
