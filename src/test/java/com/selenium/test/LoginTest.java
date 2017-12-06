package com.selenium.test;

import org.testng.annotations.Test;

import secod_my_maven.maven.common;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginTest {
	
		static WebDriver dr;
		static String baseUrl;
		static String dir="登录";
		
		/*
		 * 设置参数值
		 */
		@BeforeClass
		public static void ff() throws Exception {
			// 设置浏览器路径
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Thinkpad\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
			// 创建一个 Chrome 的浏览器实例
			dr = new ChromeDriver();
			dr.manage().window().maximize();
			baseUrl = "http://220.180.239.200:8081/cas/login?service=http://220.180.239.200:8081/sales/cas&CLIENT_CODE=DDD_PC";
			common.dir(dir);
			
		}

		@AfterClass
		public void quit(){
			dr.quit();
		}
		/*
		 * 登录
		 */
		 @Test
		public static void startUp() throws Exception {
			// 登录
			dr.get(baseUrl);
			// 等待页面加载
			WebDriverWait wait = new WebDriverWait(dr, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name("submit")));
			// 输入用户名
			dr.findElement(By.id("username")).clear();
			dr.findElement(By.id("username")).sendKeys(common.username);
			// 输入密码
			dr.findElement(By.id("password")).clear();
			dr.findElement(By.id("password")).sendKeys(common.password);
			String captcha=common.captcha(dr);
			dr.findElement(By.id("captcha")).sendKeys(captcha);
			// 记住账号
			dr.findElement(By.id("rememberZh")).click();
			
			String page = "登录界面";
			common.screen(dr, page,dir);
			// 点击登录
			dr.findElement(By.name("submit")).click();
			//star();
		}

	}

