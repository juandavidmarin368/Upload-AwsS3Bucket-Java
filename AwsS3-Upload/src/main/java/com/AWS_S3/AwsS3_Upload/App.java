package com.AWS_S3.AwsS3_Upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.StorageClass;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App 
{
    public static void main( String[] args )
    {
    	
    	
    	 CodeSource codeSource = App.class.getProtectionDomain().getCodeSource();
         File jarFile = null;
         try {
             jarFile = new File(codeSource.getLocation().toURI().getPath());
         } catch (URISyntaxException ex) {
             Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
         }
         String jarDir = jarFile.getParentFile().getPath();
         System.out.println("PATH " + jarDir);

         Properties props = new Properties();
         FileInputStream in = null;
         try {
             in = new FileInputStream(jarDir+"/config.properties");
         } catch (FileNotFoundException ex) {
             Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
             props.load(in);
         } catch (IOException ex) {
             Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
             in.close();
         } catch (IOException ex) {
             Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
         }

         
         String accesKey = props.getProperty("accessKey");
         String secretKey = props.getProperty("secretKey");
    	
        

         /* Create S3 Client Object */
            	        AmazonS3 s3 = AmazonS3ClientBuilder
            	                .standard()
            	                .withRegion(Regions.US_EAST_1)
            	                .withCredentials(new AWSStaticCredentialsProvider(
            	                                     new BasicAWSCredentials(accesKey,secretKey)))
            	                .build();       
            	         
            	        /* Set Bucket Name */
            	        String bucketName = args[0];
            	        //bucketname/db1";
            	         
            	        InputStream inputStream = null;
            	                 
            	        try {           
            	             
            	            /* Create File Object */
            	            File file = new File(args[1]);
            	             
            	            /* Set Object Key */
            	            String objectKey = file.getName();
            	             
            	            /* Send Put Object Request */
            	            PutObjectResult result = s3.putObject(bucketName, objectKey, file);
            	             
            	            /* Second Way of Putting Object */
            	             
            	            /* Create File Object */
            	            //file = new File("C:/images/S4.png");
            	             
            	            /* Create InputStream Object */
            	            try {
            					inputStream = new FileInputStream(file);
            				} catch (FileNotFoundException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
            	             
            	            /* Set Object Key */
            	            objectKey = file.getName();
            	          
            	          
            	            ObjectMetadata metadata = new ObjectMetadata();
            	          /*  Map<String,String> map = new HashMap<>();
            	            map.put("ImageType", "PNG");
            	             
            	            metadata.setUserMetadata(map);*/
            	             
            	            /* Set Content Length */
            	            metadata.setContentLength(file.length());
            	             
            	            /* Create PutObjectRequest Object */
            	            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, inputStream, metadata);
            	             
            	            /* Set StorageClass as Standard Infrequent Access */
            	            request.setStorageClass(StorageClass.StandardInfrequentAccess);
            	             
            	            /* Set Canned ACL as BucketOwnerFullControl */
            	            request.setCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
            	             
            	            /* Send Put Object Request */
            	            result = s3.putObject(request);
            	           
            	            
            	        } catch (AmazonServiceException  e) {
            	             
            	            System.out.println(e.getMessage());            
            	        } 
            	        
            	        if (inputStream != null) {
            	            try {
            					inputStream.close();
            				} catch (IOException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}                
            	        }
            	        if (s3 != null) {
            	            s3.shutdown();
            	        }
            	        
         
    	 System.out.println("Process ended.....[OK] ");
        
       
    }
}
