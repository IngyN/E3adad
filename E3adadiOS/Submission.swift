//
//  Submission.swift
//  E3adadiOS
//
//  Created by Ingy on 7/16/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import Foundation
import UIKit

public enum status {
    case late
    case pending
    case paid
}

class Submission
{
    var submission_id : String;
    var user_id : String;
    var device_id: String;
    var reading : String;
    var price : Double;
    var submission_date: NSDate?;
    var payment_date : NSDate?;
    var is_paid: status;
    var formatter: NSDateFormatter;
    
    init ()
    {
       submission_id = "233";
        user_id = "47";
        device_id = "234";
        reading = "1000kW";
        price = 1450;
        self.formatter = NSDateFormatter();
        self.formatter.dateFormat = NSDateFormatter.dateFormatFromTemplate("MMddyyyy", options: 0, locale: NSLocale(localeIdentifier: "en_GB"))
        var getFormatter : NSDateFormatter  = NSDateFormatter();
        getFormatter.dateFormat = "yyyy:MM:dd HH:mm:ss";
        submission_date = getFormatter.dateFromString("2015:07:03 07:23:26")!;
        payment_date = getFormatter.dateFromString("2015:07:03 07:23:26")!;
        is_paid = .pending;
        
    }
    
    init (id: String, userid: String, deviceid: String, read: String, price: Double, sub_date: String, pay_date: String, is_paid: status)
    {
        self.device_id = deviceid;
        self.is_paid = is_paid;
        self.submission_id = id;
        self.user_id = userid;
        self.reading = read;
        self.price = price;
//        self.payment_date = pay_date! ;
//        self.submission_date = sub_date! ;
        
        self.formatter = NSDateFormatter();
        self.formatter.dateFormat = NSDateFormatter.dateFormatFromTemplate("MMddyyyy", options: 0, locale: NSLocale(localeIdentifier: "en_GB"))
        //formatter.format now contains an optional string "dd/MM/yyyy"
        
        var getFormatter : NSDateFormatter  = NSDateFormatter();
        getFormatter.dateFormat = "yyyy:MM:dd HH:mm:ss";
        
        self.submission_date = getFormatter.dateFromString(sub_date)!;
        self.payment_date = getFormatter.dateFromString(pay_date)!;
    }
    
    func getLabel () -> String {
        
        var month :  String;
        
        var formati : NSDateFormatter = NSDateFormatter();
        
        formati.dateFormat = "MMM";
        
        month = formati.stringFromDate(submission_date!);
        
        var recorded : String = formatter.stringFromDate(submission_date!);
        
        var paid : String? = formatter.stringFromDate(payment_date!);
        
        if(paid == nil)
        {
            paid = "  --  ";
        }
        
        return "          \(month)    \(recorded)   \(paid!) ";
    }
    
    func getPrice () -> String {
        
        return "\(price)";
    }
}