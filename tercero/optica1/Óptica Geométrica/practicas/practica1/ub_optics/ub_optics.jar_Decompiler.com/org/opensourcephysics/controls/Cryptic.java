package org.opensourcephysics.controls;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Cryptic {
   private static String encoding = "UTF-8";
   private static String keyFormat = "PBEWithMD5AndDES";
   private static byte[] salt = new byte[]{9, -100, -56, 35, 30, -86, -77, 65};
   private static int interactions = 19;
   private static final String DEFAULT_PW = "ospWCMBACBJDB";
   private String cryptic;

   protected Cryptic() {
   }

   public Cryptic(String var1) {
      this.encrypt(var1);
   }

   public Cryptic(String var1, String var2) {
      this.encrypt(var1, var2);
   }

   public String encrypt(String var1) {
      return this.encrypt(var1, "ospWCMBACBJDB");
   }

   public String encrypt(String var1, String var2) {
      try {
         PBEKeySpec var3 = new PBEKeySpec(var2.toCharArray(), salt, interactions);
         SecretKey var4 = SecretKeyFactory.getInstance(keyFormat).generateSecret(var3);
         PBEParameterSpec var5 = new PBEParameterSpec(salt, interactions);
         Cipher var6 = Cipher.getInstance(var4.getAlgorithm());
         var6.init(1, var4, var5);
         byte[] var7 = var1.getBytes(encoding);
         byte[] var8 = var6.doFinal(var7);
         BASE64Encoder var9 = new BASE64Encoder();
         this.cryptic = var9.encode(var8);
      } catch (InvalidAlgorithmParameterException var10) {
      } catch (InvalidKeySpecException var11) {
      } catch (NoSuchPaddingException var12) {
      } catch (NoSuchAlgorithmException var13) {
      } catch (InvalidKeyException var14) {
      } catch (IOException var15) {
      } catch (IllegalBlockSizeException var16) {
      } catch (BadPaddingException var17) {
      }

      return this.cryptic;
   }

   public String decrypt() {
      return this.decrypt("ospWCMBACBJDB");
   }

   public String decrypt(String var1) {
      try {
         PBEKeySpec var2 = new PBEKeySpec(var1.toCharArray(), salt, interactions);
         SecretKey var3 = SecretKeyFactory.getInstance(keyFormat).generateSecret(var2);
         PBEParameterSpec var4 = new PBEParameterSpec(salt, interactions);
         Cipher var5 = Cipher.getInstance(var3.getAlgorithm());
         var5.init(2, var3, var4);
         BASE64Decoder var6 = new BASE64Decoder();
         byte[] var7 = var6.decodeBuffer(this.cryptic);
         byte[] var8 = var5.doFinal(var7);
         return new String(var8, encoding);
      } catch (InvalidAlgorithmParameterException var9) {
      } catch (InvalidKeySpecException var10) {
      } catch (NoSuchPaddingException var11) {
      } catch (NoSuchAlgorithmException var12) {
      } catch (InvalidKeyException var13) {
      } catch (IOException var14) {
      } catch (IllegalBlockSizeException var15) {
      } catch (BadPaddingException var16) {
      }

      return null;
   }

   public String getCryptic() {
      return this.cryptic;
   }

   public void setCryptic(String var1) {
      this.cryptic = var1;
   }

   public static XML.ObjectLoader getLoader() {
      return new Cryptic.Loader();
   }

   static class Loader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         Cryptic var3 = (Cryptic)var2;
         var1.setValue("cryptic", var3.getCryptic());
      }

      public Object createObject(XMLControl var1) {
         return new Cryptic();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Cryptic var3 = (Cryptic)var2;
         var3.setCryptic(var1.getString("cryptic"));
         return var2;
      }
   }
}
