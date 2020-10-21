package selenium;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class CircleJupiterTest {

    @Test
    void testWithOneFirefox(FirefoxDriver driver) {
        driver.get("http://localhost:8080/circle.jsp");
        assertThat(driver.getTitle(), containsString("Circle Calculator"));
        WebElement radiusElement = driver.findElement(By.id("radius"));
        radiusElement.clear();
        radiusElement.sendKeys("125");
        WebElement submitElement = driver.findElement(By.id("submit"));
        submitElement.click();
        
        
    }


}