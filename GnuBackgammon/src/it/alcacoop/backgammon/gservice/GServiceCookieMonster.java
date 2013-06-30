package it.alcacoop.backgammon.gservice;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;


public class GServiceCookieMonster implements GServiceMessages {
  private final static boolean DEBUG = false;

  private class CookieDough {
    public Pattern regex = null;
    public int message = 0;
  }

  private LinkedList<CookieDough> numericBatch;
  
  public GServiceCookieMonster() {
    prepareBatches();
  }
  
  public int fIBSCookie(String message) {
    int result = 0;
    Iterator<GServiceCookieMonster.CookieDough> iter;
    CookieDough ptr = null;

    iter = numericBatch.iterator();
    while (iter.hasNext()) {
      ptr = iter.next();
      if (ptr.regex.matcher(message).find()) {
        result = ptr.message;
        break;
      }
    }

    if (result == 0) return(0);
    
    String[] ss = ptr.regex.split(message, 2);
    if (ss.length > 1 && ss[1].length() > 0) {
      if (DEBUG) {
        System.out.println("cookie = " + result);
        System.out.println("message = '" + message + "'");
        System.out.println("Leftover = '" + ss[1] + "'");
      }
    }
    return(result);
  }



  LinkedList<CookieDough> currentBatchBuild;
  private void addDough(int msg, String re) {
    CookieDough newDough = new CookieDough();
    newDough.regex = Pattern.compile(re);
    newDough.message = msg;
    currentBatchBuild.add(newDough);
  }


  private void prepareBatches() {
    currentBatchBuild = new LinkedList<CookieDough>();
    addDough(GSERVICE_CONNECTED, "^"+GSERVICE_CONNECTED);
    addDough(GSERVICE_READY, "^"+GSERVICE_READY);
    addDough(GSERVICE_HANDSHAKE, "^"+GSERVICE_HANDSHAKE);
    addDough(GSERVICE_BYE, "^"+GSERVICE_BYE);
    this.numericBatch = this.currentBatchBuild;
  }
}
