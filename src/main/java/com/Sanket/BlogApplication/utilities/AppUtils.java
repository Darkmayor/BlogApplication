package com.Sanket.BlogApplication.utilities;

import java.io.File;

public class AppUtils{
    public static String getUploadPath(String fileName){
        return new File("src\\main\\resources\\static\\images\\Uploads").getAbsolutePath() + "\\" + fileName;
    }
}