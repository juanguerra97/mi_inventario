package inventario.modelo.Validacion;

import static org.junit.Assert.*;

import org.junit.Test;

import inventario.modelo.validacion.Validacion;

public class TestRegexModelo {

	@Test
	public void testRegexEmail() {
		String email1 = "username87@host.com";
		assertTrue(email1.matches(Validacion.REGEX_EMAIL));
		String email2 = "name_45@umg.edu.gt";
		assertTrue(email2.matches(Validacion.REGEX_EMAIL));
		String email3 = "chepe_456@gmail.com";
		assertTrue(email3.matches(Validacion.REGEX_EMAIL));
		String email4 = "bad.username@host.com";
		assertTrue(email4.matches(Validacion.REGEX_EMAIL));
		String email5 = "user@bad host.es";
		assertFalse(email5.matches(Validacion.REGEX_EMAIL));
		String email6 = "user1@badhost";
		assertFalse(email6.matches(Validacion.REGEX_EMAIL));
		String email7 = "user@badhost.h";
		assertFalse(email7.matches(Validacion.REGEX_EMAIL));
		String email8 = "bademail.me";
		assertFalse(email8.matches(Validacion.REGEX_EMAIL));
	}
	
	@Test
	public void testRegexDPI() {
		String dpi1 = "1234567891234";
		assertTrue(dpi1.matches(Validacion.REGEX_DPI));
		String dpi2 = "1";
		assertFalse(dpi2.matches(Validacion.REGEX_DPI));
		String dpi3 = "0123467891023";
		assertFalse(dpi3.matches(Validacion.REGEX_DPI));
		String dpi4 = "1234567890123";
		assertFalse(dpi4.matches(Validacion.REGEX_DPI));
	}
	
	@Test
	public void testRegexTelefono() {
		String tel1 = "34567891";
		assertTrue(tel1.matches(Validacion.REGEX_TELEFONO));
		String tel2 = "123";
		assertFalse(tel2.matches(Validacion.REGEX_TELEFONO));
		String tel3 = "01234679";
		assertFalse(tel3.matches(Validacion.REGEX_TELEFONO));
		String tel4 = "1234E567";
		assertFalse(tel4.matches(Validacion.REGEX_TELEFONO));
	}

}
