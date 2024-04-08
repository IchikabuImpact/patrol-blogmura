### Selenuim をEclipse JAVAで動かしてみる

## 前提：
OS:WINDOWS11 HOME
IDEの想定バージョン：Eclipse2024(pleiades)
javaの想定バージョン：jdk17 jre17 
chromedriver.exe 123.0.6312.105
Google Chrome Version 123.0.6312.106 (Official Build) (64-bit)

## 大事なこと
chromeとchrome driverは同じバージョンを使うこと
ここではv123とする

## ■JDK17　JRE17を入手しインストールします。
JDK17
https://www.oracle.com/jp/java/technologies/downloads/#java17

JRE17は次の手順でC:\pleiades\2024-03\javaに含まれると思います。

## ■Eclipse(pleadeas)を javaに印がついているもの入手＋インストール
https://willbrains.jp/index.html#/pleiades_distros2024.html
※デフォルトだとC:\pleiades\2024-03\eclipseにインストールされます。

## ■Eclipseプロジェクトを作成・設定
ファイル＞新規>プロジェクト>JAVAプロジェクトで新規プロジェクト作ります


## ■Eclipseにてコンパイラーの設定
プロジェクト名＞右クリック＞プロパティー＞Javaコンパイラー＞ワークスペース設定の構成...>コンパイラー準拠レベル17

## ■Eclipseプロジェクトで必要なフォルダを構成
プロジェクト直下にexeとlibがなかったら手動でフォルダを用意してください。
srcにはMain.javaを用意

## ■chromedriver.exe を入手　最新のものが良い
https://googlechromelabs.github.io/chrome-for-testing/#stable
※zipへのURLをブラウザでたたいて解凍するとchromedriver.exeなど実行ファイルがあると思います。

※実行ファイルをEclipseプロジェクトのexeフォルダに配置しておいてください。

※私は以下を利用しました。
https://storage.googleapis.com/chrome-for-testing-public/123.0.6312.105/win64/chromedriver-win64.zip

## ■Chrome Driverと同じversionのGoogle Chromeをインストールする
https://google-chrome.jp.uptodown.com/windows/versions

なるべく新しいモノでversionを揃えましょう。

## ■seleniumをダウンロード　javaのリンクからダウンロード＋解凍
https://selenium.dev/downloads/
seleniumを解凍したらEclipseプロジェクトにlibフォルダを作ってjarを配置しましょう。
配置したらEclipseプロジェクトをリロードし
プロジェクト名から右クリック＞プロパティー＞ライブラリからJARの追加で
libにあるもjarを全部選択し使えるようにします。

## ■サンプルソース　ブログ村で作った記事を巡回してみましょう
```
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
       String email = readFromFile(Paths.get("email.txt").toString());
       String password = readFromFile(Paths.get("password.txt").toString());
       String url = readFromFile(Paths.get("url.txt").toString());
       System.setProperty("webdriver.chrome.driver", "./exe/chromedriver.exe");
       WebDriver driver = new ChromeDriver();
       driver.get("https://mypage.blogmura.com/");
       login(driver, email, password);
       driver.get(url);
       Thread.sleep(2000);
       driver.quit();
   }
   private static String readFromFile(String path) throws IOException {
       try (Scanner scanner = new Scanner(new File(path))) {
           return scanner.nextLine();
       }
   }
   private static void login(WebDriver driver, String email, String password) {
       WebElement emailBox = driver.findElement(By.name("email"));
       WebElement passwordBox = driver.findElement(By.name("password"));
       WebElement loginButton = driver.findElement(By.className("re-button-submit-small"));
       emailBox.sendKeys(email);
       passwordBox.sendKeys(password);
       loginButton.click();
   }
}
```
## ■サンプルソースの説明
プロジェクト直下に3つのファイルを用意して読み込む想定になってます。
email.txt　
ブログ村のemail
password.txt
ブログ村アカウントのパスワード
url.txt 
ブログら村で巡回したいURLここはコツがありまして
ブログ村のプロフィールを見に行くと新着で自分の記事のURLが表示されます。
そこを右クリックURLをコピーしてこのテキストにはるわけです。
＜例＞こんな感じのURLになると思います。
https://link.blogmura.com/out/?ch=YOURCHANNEL&item=YOURITEM&url=https%3A%2F%2FYOURPAGE.html
