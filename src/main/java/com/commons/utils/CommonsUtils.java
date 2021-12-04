package com.commons.utils;

import java.beans.BeanInfo;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.commons.exceptions.BusinessException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonsUtils {

	
	private static final Logger logger = LoggerFactory.getLogger(CommonsUtils.class);
	
	public static MultiValueMap<String, Object>  introspect(Object obj) throws Exception {
		MultiValueMap<String, Object> result = new LinkedMultiValueMap<String, Object>();
		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			if(!"class".equals(pd.getName())){
				Method reader = pd.getReadMethod();
				if (reader != null)
					result.add(pd.getName(), reader.invoke(obj));
			}
		}
		return result;
	}

	public static <T> String convertJsonObjectToXml (T obj) throws BusinessException {

		JAXBContext jaxbContext;
		String xml = null;
		try {
			OutputStream os = new ByteArrayOutputStream();
			jaxbContext = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();	
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(obj, os);
			xml = os.toString();
		} catch (JAXBException e) {
			logger.error("XML converter exception: ", e);
			throw new BusinessException("XML converter exception: ", e);
		}
		return xml;
	}

	public static <T> Object convertXmlToObject (String xml, Class<T>typeObject) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject xmlJsonObject = XML.toJSONObject(xml);
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		Object obj = null;
		try {
			obj = mapper.readValue(xmlJsonObject.toString(), typeObject);
		} catch (IOException e) {
			logger.error("Convert xml to object exception: ", e);
			throw new BusinessException ("Convert xml to object exception: ", e);
		}
		return obj;
	}

	public static String encodeRequestBase64 (String requestXML) {
		byte[]requestXmlBytes = requestXML.getBytes();
		byte[]requestXmlBytesBase64 = Base64.encodeBase64(requestXmlBytes);
		String requestXmlBase64 = new String (requestXmlBytesBase64);
		return requestXmlBase64;
	}

	public static String getRamdomString(Integer maxCharacters, boolean isPassword){
		char[] chars; 

		Random random = new Random();

		if(isPassword){

			chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#".toCharArray();
			while(true) {
				char[] password = new char[maxCharacters];
				boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
				for(int i=0; i<maxCharacters; i++) {
					char ch = chars[random.nextInt(chars.length)];
					if(Character.isUpperCase(ch))
						hasUpper = true;
					else if(Character.isLowerCase(ch))
						hasLower = true;
					else if(Character.isDigit(ch))
						hasDigit = true;
					else
						hasSpecial = true;
					password[i] = ch;
				}
				if(hasUpper && hasLower && hasDigit && hasSpecial) {
					return new String(password);
				}
			}

		}else{
			chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < maxCharacters; i++) {
				char c = chars[random.nextInt(chars.length)];
				sb.append(c);
			}		
			return sb.toString();
		}
	}
	
	
	
	/**
	 * Convierte una fecha en formato integer yyyyMMdd a string dd/MM/yyyy
	 *
	 * @param dateToConvert the date to convert
	 * @return la fecha fromateada
	 * @throws ParseException the parse exception
	 */
	public static String convertAndFormatIntegerDate(Integer dateToConvert) throws ParseException{
		
		if (dateToConvert != null) {
			SimpleDateFormat sdfForInteger = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdfForResult = new SimpleDateFormat("dd/MM/yyyy");
			
			Date parsedIntegerDate = sdfForInteger.parse(dateToConvert.toString()); 
			
			return sdfForResult.format(parsedIntegerDate);
		} else {
			return null;
		}
	}
	
	public static Integer convertAndFormatStringDate (String dateToConvert) throws ParseException {
		
		if (dateToConvert != null) {
			
			SimpleDateFormat sdfForString = new SimpleDateFormat("dd/MM/yyyy");
			
			SimpleDateFormat sdfForResult = new SimpleDateFormat("yyyyMMdd");
			
			Date fechaFormat = sdfForString.parse(dateToConvert);
			
			Integer fehaInteger = Integer.parseInt(sdfForResult.format(fechaFormat));
			
			return fehaInteger;
		} else {
			return null;
		}
	}
	
	public static Date convertAndFormatStringDateHours (String dateHours) throws ParseException {
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Date fechaHora = format.parse(dateHours);
		
		return fechaHora;
	}
	
	public static Date getDateFromString(String dateToConvert) throws ParseException {
		if (dateToConvert != null) {
			SimpleDateFormat sdfForString = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaFormat = sdfForString.parse(dateToConvert);
			return fechaFormat;
		} else {
			return null;
		}
	}
	
	public static Date getDateHoursFromStringDateStringHours (String date, String hours) throws ParseException {
		
		if (date != null && hours != null) {
			SimpleDateFormat sdfForString = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date fechaFormat = sdfForString.parse(date + " " + hours);
			return fechaFormat;
		} else {
			return null;
		}
	}
	
	public static long diferenciaEnMinutosEntreHoras (Date fechaInicioReserva, Date fechaActual) {
				
		Calendar cFechaInicioReserva = Calendar.getInstance();
		Calendar cFechaActual = Calendar.getInstance();
		
		cFechaInicioReserva.setTime(fechaInicioReserva);
		
		cFechaActual.setTime(fechaActual);
		
		long fechaInicioReservaMilis = cFechaInicioReserva.getTimeInMillis();
		
		long fechaActualMilis = cFechaActual.getTimeInMillis();
		
		long diferencia = fechaActualMilis - fechaInicioReservaMilis;
		
		long diferenciaMinutos = Math.abs(diferencia/(60*1000));
		
		return diferenciaMinutos;
	}
}