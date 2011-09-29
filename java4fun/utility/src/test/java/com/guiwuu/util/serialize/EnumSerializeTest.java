package com.guiwuu.util.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class EnumSerializeTest {
	
	@Test
	public void testEnumHessianSerialize() throws Exception{
		TestEnumDO testDo= new TestEnumDO();
		testDo.setT(TestEnum.B);
		FileOutputStream fos = new FileOutputStream("d:/enumtest2");
	    HessianOutput out = new HessianOutput(fos);  
	    out.writeObject(testDo);  
	    out.flush();  
	}
	
	@Test
	public void testEnumHessianDeserialize() throws Exception{
		FileInputStream fis = new FileInputStream("d:/enumtest2");  
	    HessianInput in = new HessianInput(fis);  
	    TestEnumDO read = (TestEnumDO) in.readObject(TestEnumDO.class);  
	    System.out.println(read);  
	}
	
	@Test
	public void testEnumJavaSerialize() throws Exception{
		TestEnumDO testDo= new TestEnumDO();
		testDo.setT(TestEnum.B);
		
		FileOutputStream fos = new FileOutputStream("d:/enumtest3");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(testDo);
		oos.flush();
	}
	
	@Test
	public void testEnumJavaDeserialize() throws Exception{
		FileInputStream fis = new FileInputStream("d:/enumtest3");  
	    ObjectInputStream oin = new ObjectInputStream(fis);  
	    TestEnumDO read = (TestEnumDO)oin.readObject();
	    System.out.println(read);  
	}

}

