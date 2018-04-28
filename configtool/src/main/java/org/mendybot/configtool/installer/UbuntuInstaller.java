package org.mendybot.configtool.installer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UbuntuInstaller extends UnixInstaller
{
  private File etcDir;
  private File archiveEtcDir;
  private File etcNetworkDir;
  private File archiveNetworkDir;
  private ArrayList<Host> hosts = new ArrayList<Host>();
  private ArrayList<NfsLink> links = new ArrayList<NfsLink>();
  private ArrayList<NetworkInterface> interfaces = new ArrayList<NetworkInterface>();

  public UbuntuInstaller()
  {
    Network netStation = new Network("station-net", 192, 168, 1, 0, 24);
    Network netMendyBot = new Network("mendy-net", 192, 168, 2, 0, 24);

    Host station1 = new Host("MendyStation-1");
    station1.add(new NetworkInterface("enp0s8"));
    station1.add(new NetworkInterface("enp0s9"));
    station1.lookupInterface("enp0s8").plumb(netStation.lookupNode((byte)3));
//    station1.lookupInterface("enp0s9").plumb(netMendyBot.lookupNode((byte)3));
    
    Host station2 = new Host("MendyStation-2");
    station2.add(new NetworkInterface("enp0s8"));
    station2.add(new NetworkInterface("enp0s9"));
    station2.lookupInterface("enp0s8").plumb(netStation.lookupNode((byte)4));
//    station2.lookupInterface("enp0s9").plumb(netStation.lookupNode((byte)4));
    
    Host nas1 = new Host("MB-NAS-1");
    nas1.add(new NetworkInterface("enp0s8"));
    nas1.lookupInterface("enp0s8").plumb(netStation.lookupNode((byte)11));
    
    // cluster
    Host station = new Host("MendyStation");
//    station.add(new NetworkInterface("enp0s8"));
    station.add(new NetworkInterface("enp0s9"));
//    station.lookupInterface("enp0s8").plumb(netMendyBot.lookupNode((byte)2));
    station.lookupInterface("enp0s9").plumb(netMendyBot.lookupNode((byte)2));

    hosts.add(station1);
    hosts.add(station2);
    hosts.add(nas1);
    hosts.add(station);
    
    links.add(new NfsLink(nas1, "/mnt/MendyStore-0/MendyStation-0", "/station", 8192 , 8192, 14));
    
//    interfaces.add(new NetworkInterface("enp0s8", "192.168.1.3", "255.255.255.0", "192.168.1.1"));
//    interfaces.add(new NetworkInterface("enp0s9", "192.168.2.2", "255.255.255.0", "192.168.2.1"));
    
    interfaces.addAll(station1.getInterfaces());
  }
  
  @Override
  public void initUnixInstaller(boolean testMode)
  {
    File archiveDir;
    if (testMode) {
      etcDir = new File("installer/etc");
      archiveDir = new File("installer/opt/MendyBot/archive");
    } else {
      etcDir = new File("/etc");
      archiveDir = new File("/opt/MendyBot/archive");
    }
    etcNetworkDir = new File(etcDir, "network");
    
    archiveEtcDir = new File(archiveDir, "etc");
    if (!archiveEtcDir.exists()) {
      archiveEtcDir.mkdirs();
    }
    archiveNetworkDir = new File(archiveEtcDir, "network");
    if (!archiveNetworkDir.exists()) {
      archiveNetworkDir.mkdirs();
    }
  }

  @Override
  public void startUnixInstaller()
  {
    initArchive("hosts", etcDir, archiveEtcDir);
    initArchive("fstab", etcDir, archiveEtcDir);
    initArchive("interfaces", etcNetworkDir, archiveNetworkDir);
    
    try
    {
      generateHosts(etcDir, archiveEtcDir);
      generateFsTab(etcDir, archiveEtcDir);
      generateInterfaces(etcNetworkDir, archiveNetworkDir);
    }
    catch (IOException e)
    {
      restoreArchive("hosts", etcDir, archiveEtcDir);
      restoreArchive("fstab", etcDir, archiveEtcDir);
      restoreArchive("interfaces", etcNetworkDir, archiveNetworkDir);
    }
  }

  private void generateHosts(File dir, File archiveDir) throws IOException
  {
    File f = new File(dir, "hosts");
    File a = new File(archiveDir, "hosts");
    
    PrintWriter pw = new PrintWriter(new FileWriter(f));
    populate(pw, a);
    
    for (Host h : hosts)
    {
      h.writeHostsEntries(pw);
    }
    
    pw.close();
  }

  private void generateFsTab(File dir, File archiveDir) throws IOException
  {
    File f = new File(dir, "fstab");
    File a = new File(archiveDir, "fstab");
    
    PrintWriter pw = new PrintWriter(new FileWriter(f));
    populate(pw, a);
        
    for (NfsLink link : links)
    {
      pw.println(link.toString());
    }
    
    pw.close();
  }

  private void generateInterfaces(File dir, File archiveDir) throws IOException
  {
    File f = new File(dir, "interfaces");
    File a = new File(archiveDir, "interfaces");
    
    PrintWriter pw = new PrintWriter(new FileWriter(f));
    populate(pw, a);
    
    for (NetworkInterface i : interfaces)
    {
      if (i.hasAddressString()) {
        pw.println(i.toString());
      }
    }
    
    pw.close();
  }

  private void populate(PrintWriter pw, File a) throws IOException
  {
    BufferedReader br = new BufferedReader(new FileReader(a));
    String line;
    while((line = br.readLine())!= null)
    {
      pw.println(line);
    }
    br.close();
    pw.println();
    pw.println("# ################### MendyBot config line ###################");
  }

  private void initArchive(String fileName, File dir, File archiveDir)
  {
    File f = new File(dir, fileName);
    File a = new File(archiveDir, fileName);
    if (!a.exists()) {
      boolean success = copyFile(f, a);
      System.out.println(success);
    }
  }

  private boolean copyFile(File f, File a)
  {
    String command = "cp " + f.getPath()+" "+a.getPath();
    try
    {
      return executeCommand(command);
    }
    catch (IOException e)
    {
      return false;
    }
  }

  private boolean executeCommand(String command) throws IOException
  {
    Runtime run = Runtime.getRuntime();
    try
    {
      Process proc = run.exec(command);
      proc.waitFor();
      return true;
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
      return false;
    }
  }

  private void restoreArchive(String fileName, File dir, File archiveDir)
  {
    File f = new File(dir, fileName);
    File a = new File(archiveDir, fileName);
    if (!a.exists()) {
      boolean success = copyFile(a, f);
    }
  }

}
