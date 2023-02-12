package lk.matrix.soysaaquarium.Services;
import com.fazecast.jSerialComm.SerialPort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
public class ArduinoToSql {

    private SerialPort serialPort;
    private double temperature;

    public ArduinoToSql(){

        try{
            SerialPort[] allAvaiableComPorts = SerialPort.getCommPorts();
            if(allAvaiableComPorts.length == 0){
                System.out.printf("no ports available");
            }
            System.out.println("com ports: "+Arrays.toString(allAvaiableComPorts));
            serialPort = allAvaiableComPorts[0];

            serialPort.setBaudRate(9600);

            boolean connected = serialPort.openPort();

            if(connected){
                System.out.println("successful connection to arduino at port "+ serialPort);
            }

            try {
                if (serialPort.isOpen()) {
                    System.out.println( serialPort.getDescriptivePortName()+" is running !");
                    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0,0);

                    Thread threadCP = new Thread(() -> {
                        Scanner scanner = new Scanner(serialPort.getInputStream());
                        while(scanner.hasNextLine()){
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {}

                            String line = scanner.next();
                            temperature = Double.parseDouble(line);
                            System.out.println(temperature);
                            //INSERT INTO tempRecords(timeStampTemp, tankId,temperature) VALUES (NOW(), 1, 31.00);
                            //db connection
                            try{
                                Random r = new Random();
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
                                Statement statement = connection.createStatement();
                                statement.executeUpdate("INSERT INTO tempRecords(timeStampTemp, tankId,temperature) VALUES (NOW(), 'T001', "+temperature+");");
                                statement.executeUpdate("INSERT INTO tempRecords(timeStampTemp, tankId,temperature) VALUES (NOW(), 'T002', "+temperature+");");
                                statement.executeUpdate("INSERT INTO ammoniaRecords(timeStampAmmo, tankId,ammonia) VALUES (NOW(), 'T001', "+(0.0+(1.0-0.0)*r.nextDouble())+");");
                                statement.executeUpdate("INSERT INTO ammoniaRecords(timeStampAmmo, tankId,ammonia) VALUES (NOW(), 'T002', "+(0.0+(1.0-0.0)*r.nextDouble())+");");
                                statement.executeUpdate("INSERT INTO phRecords(timeStampPH, tankId,pH) VALUES (NOW(), 'T001', "+(3.0+(7.0-3.0)*r.nextDouble())+");");
                                statement.executeUpdate("INSERT INTO phRecords(timeStampPH, tankId,pH) VALUES (NOW(), 'T002', "+(3.0+(7.0-3.0)*r.nextDouble())+");");

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        scanner.close();
                    });
                    threadCP.start();
                }
            } catch (Exception e) {
            }

        }catch (Exception e){
            System.out.println("arduino connection failed");
            System.out.println("arduino connection failed");
//            addDummyData();
            Thread newThread = new Thread(() -> {
                while (true){
                    try{
                        Random r = new Random();
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
                        Statement statement = connection.createStatement();
                        statement.executeUpdate("INSERT INTO tempRecords(timeStampTemp, tankId,temperature) VALUES (NOW(), 'T001', "+(30.0+(33.0-30.0)*r.nextDouble())+");");
                        statement.executeUpdate("INSERT INTO tempRecords(timeStampTemp, tankId,temperature) VALUES (NOW(), 'T002', "+(30.0+(33.0-30.0)*r.nextDouble())+");");
                        statement.executeUpdate("INSERT INTO ammoniaRecords(timeStampAmmo, tankId,ammonia) VALUES (NOW(), 'T001', "+(0.0+(1.0-0.0)*r.nextDouble())+");");
                        statement.executeUpdate("INSERT INTO ammoniaRecords(timeStampAmmo, tankId,ammonia) VALUES (NOW(), 'T002', "+(0.0+(1.0-0.0)*r.nextDouble())+");");
                        statement.executeUpdate("INSERT INTO phRecords(timeStampPH, tankId,pH) VALUES (NOW(), 'T001', "+(3.0+(7.0-3.0)*r.nextDouble())+");");
                        statement.executeUpdate("INSERT INTO phRecords(timeStampPH, tankId,pH) VALUES (NOW(), 'T002', "+(3.0+(7.0-3.0)*r.nextDouble())+");");
                        Thread.sleep(5000);
                    }catch (Exception err){
                        err.printStackTrace();
                    }

                }

            });

            newThread.start();
        }


    }
}
