import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        // email.txtとpassword.txtのパス
        String emailPath = Paths.get("email.txt").toString();
        String passwordPath = Paths.get("password.txt").toString();
        String urlListPath = Paths.get("url.txt").toString();
        
        // テキストファイルからemailとpasswordを読み込む
        String email = readFromFile(emailPath);
        String password = readFromFile(passwordPath);
        String url = readFromFile(urlListPath);


        System.setProperty("webdriver.chrome.driver", "./exe/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://mypage.blogmura.com/");

        // ログイン
        login(driver, email, password);

        // ブログ巡回
        driver.get(url);
        
        // スリープ
        Thread.sleep(2000);

        // 終了
        driver.quit();
    }
    

    private static String readFromFile(String path) throws IOException {
        try (Scanner scanner = new Scanner(new File(path))) {
            return scanner.nextLine();
        }
    }

    private static void login(WebDriver driver, String email, String password) {
        // email入力
        WebElement emailBox = driver.findElement(By.name("email"));
        emailBox.sendKeys(email);

        // password入力
        WebElement passwordBox = driver.findElement(By.name("password"));
        passwordBox.sendKeys(password);

        // ログインボタンクリック
        WebElement loginButton = driver.findElement(By.className("re-button-submit-small"));
        loginButton.click();
    }
}