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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionDtoTest {
    @Test
    public void testMainMethods(){
        assertThat(ExceptionDto.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(),
                hasValidBeanEquals(), hasValidBeanToString(), hasValidBeanHashCode()));
    }
}
