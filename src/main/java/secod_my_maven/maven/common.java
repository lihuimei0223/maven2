package secod_my_maven.maven;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class common {
	public static String username="xxs";
	public static String password="ab123456";
	public static String farmname="小香蕉";
	public static String farmphone="18900000004";
	public static String detailAdrress="万科金域华府13栋1102";
	public static String idCard="324561718908675432";
	public static String lsSellName="测试零售商";
	public static String lsSellPhone="18190909819";
	// 页面加载
	public static void time(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 页面截屏
			public static void screen(WebDriver dr, String page,String dir) {
				File scrFile = ((TakesScreenshot) dr).getScreenshotAs(OutputType.FILE);
				String dirName="F:\\lhm\\SeleniumJavaphoto\\photo\\" + dir + "\\";
				try {
					System.out.print("1");
					FileUtils.copyFile(scrFile, new File(dirName  + page + ".png"));
				} catch (Exception e) {
					System.out.print("2");
					e.printStackTrace();
				} finally {
					System.out.print("3");
				}
			}
	
	/**
	 * This method for get captcha
	 * 
	 * @param dr
	 */

	public static String captcha(WebDriver dr) throws IOException, InterruptedException {
		//获取验证码图片
		WebElement element = dr.findElement(By.xpath("//*[@id='fm1']/ul/li[3]/em/img"));
        //保存验证码图片
		screenShotForElement(dr, element, "C:\\Program Files (x86)\\Tesseract-OCR\\test.png");
		Runtime rt = Runtime.getRuntime();
		//将图片文字转化为文本
		rt.exec("cmd.exe   /C   start /b   F:\\lhm\\tesseract.bat");
		Thread.sleep(1000);
		// 读取文本，图片存放位置需要和tesseract.exe在一起
		return readTextFile("C:\\Program Files (x86)\\Tesseract-OCR\\test.txt");

	}

	/**
	 * This method for read TXT file
	 * 
	 * @param filePath
	 */
	public static String readTextFile(String filePath) {
		String captcha = null;
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);

				while ((captcha = bufferedReader.readLine()) != null) {
					System.out.println(captcha);

					return captcha;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return captcha;
	}

	/**
	 * This method for screen shot element
	 * 
	 * @param dr
	 * @param element
	 * @param path
	 * @throws InterruptedException
	 */
	public static void screenShotForElement(WebDriver dr, WebElement element, String path)
			throws InterruptedException {
		File scrFile = ((TakesScreenshot) dr).getScreenshotAs(OutputType.FILE);
		try {
			Point p = element.getLocation();
			int width = element.getSize().getWidth();
			int height = element.getSize().getHeight();
			Rectangle rect = new Rectangle(width, height);
			BufferedImage img = ImageIO.read(scrFile);
			BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
			ImageIO.write(dest, "png", scrFile);
			Thread.sleep(1000);
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close(WebDriver dr,String parentWindowId,String dir) {
		//获得所有窗口的句柄
        Set<String> allWindowsId = dr.getWindowHandles();
        // 获取非父窗口句柄并关闭窗口
        for (String windowId : allWindowsId) {
            if (!windowId.equals(parentWindowId)) {
                dr.switchTo().window(windowId);
                //截屏小票页面
                String page1 = "打印小票";
        		common.screen(dr, page1,dir);
               //关闭小票页面
                dr.close();
                break;
            }
        }
        //回到父窗口界面
        dr.switchTo().window(parentWindowId);
	    
	}
	
	/**
	 * 上传图片
	 * @param dr
	 */
	
public static void uploadPhoto(WebDriver dr){
		
		//切换为默认的frame
		dr.switchTo().defaultContent();
		//切换为上传图片frame
		dr.switchTo().frame(dr.findElement(By.tagName("iframe"))); 
		//加载页面
		new WebDriverWait(dr,5).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#filePicker > div.webuploader-pick")));
		//单击选择图片                                                                     
		dr.findElement(By.tagName("label")).click();
		//调用本地autio上传图片
		try {
			
			Runtime.getRuntime().exec("F:\\lhm\\SeleniumJavaphoto\\photo.exe"+ " " + "chrome" + " " + "F:\\lhm\\SeleniumJavaphoto\\photo\\kid.jpg"); 
			//Runtime.getRuntime().exec("E:/SeleniumJava/photo.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}  
		//加载页面
		new WebDriverWait(dr,5).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#uploader > div.statusBar > div.btns > div.uploadBtn.state-ready")));
		//点击上传
		dr.findElement(By.cssSelector("#uploader > div.statusBar > div.btns > div.uploadBtn.state-ready")).click();
		
	}




public static void dir(String dir) {
	
	String dirName = "F:/lhm/SeleniumJavaphoto/photo/"+dir;// 创建目录
	createDir(dirName);// 调用方法创建目录

}


// 通过 sPath.matches(matches) 方法的返回值判断是否正确
// sPath 为路径字符串
static boolean flag = false;
static File file;



    /**
     * 创建目录
     * @param destDirName
     * @return
     */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在
			System.out.println("创建目录失败，目标目录已存在！");
			//删除目录下文件
			deleteDirectory(destDirName);
			return true;
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("创建目录成功！" + destDirName);
			return true;
		} else {
			System.out.println("创建目录失败！");
			return false;
		}
	}
	
	


public static boolean deleteFile(String filePath) {// 删除单个文件
	flag = false;
	file = new File(filePath);
	if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
		file.delete();// 文件删除
		flag = true;
	}
	return flag;
}

public static boolean deleteDirectory(String dirPath) {// 删除目录（文件夹）以及目录下的文件
	// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
	if (!dirPath.endsWith(File.separator)) {
		dirPath = dirPath + File.separator;
	}
	File dirFile = new File(dirPath);
	// 如果dir对应的文件不存在，或者不是一个目录，则退出
	if (!dirFile.exists() || !dirFile.isDirectory()) {
		return false;
	}
	flag = true;
	File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
	for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
		if (files[i].isFile()) {// 删除子文件
			flag = deleteFile(files[i].getAbsolutePath());
			System.out.println(files[i].getAbsolutePath() + " 删除成功");
			if (!flag)
				break;// 如果删除失败，则跳出
		} else {// 运用递归，删除子目录
			flag = deleteDirectory(files[i].getAbsolutePath());
			if (!flag)
				break;// 如果删除失败，则跳出
		}
	}
	if (!flag)
		return false;
	return flag;
	
}

   /**
    * 判断页面是否出现某个字段
    * @param dr
    * @param content
    * @return
    */

	public static boolean isContentAppeared(WebDriver dr, String content) {
		boolean status = false;
		try {
			dr.findElement(By.xpath("//*[contains(.,'" + content + "')]"));
			System.out.println(content + " is appeard!");
			status = true;
		} catch (NoSuchElementException e) {
			status = false;
			System.out.println("'" + content + "' doesn't exist!");
		}
		return status;
	}
}


