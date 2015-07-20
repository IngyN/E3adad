//
//  Statistics.swift
//  E3adadiOS
//
//  Created by Hager Rady on 7/18/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import UIKit

class Statistics: UIViewController {
    
    var stats_url = "http://baseetta.com/hatem/e3adad/stats.php?user_id="
    var cons_url  = "http://baseetta.com/hatem/e3adad/consumption.php?user_id="
    
    @IBOutlet weak var webView: UIWebView!
    
    
    @IBOutlet weak var AvgCons_label: UILabel!
    @IBOutlet weak var AvgCost_label: UILabel!
    @IBOutlet weak var TotalCons_label: UILabel!
    @IBOutlet weak var Progress_label: UILabel!
    

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let prefs: NSUserDefaults = NSUserDefaults.standardUserDefaults()
        let user_id:NSString = prefs.valueForKey("user_id") as NSString
        
        let urlOne = NSURL(string: stats_url+(user_id as String))
        let urlTwo = NSURL(string: cons_url+(user_id as String))
        
        get_otherinfo(urlTwo!)
        
        //  let requestURL = NSURL(string:api_url)
        let request = NSURLRequest(URL: urlOne!)
        webView.loadRequest(request)
        
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    // Get Other Information
    func get_otherinfo (url: NSURL)
    {
        let httpMethod = "GET"
        let timeout = 15
        let urlRequest = NSMutableURLRequest(URL: url,
            cachePolicy: .ReloadIgnoringLocalAndRemoteCacheData,
            timeoutInterval: 15.0)
        let queue = NSOperationQueue()
        
        NSURLConnection.sendAsynchronousRequest(
            urlRequest,
            queue: queue,
            completionHandler: {(response: NSURLResponse!, data: NSData!, error: NSError!) in
                if data.length > 0 && error == nil{
                    
                    let json = NSString(data: data, encoding: NSASCIIStringEncoding)
                    self.extract_data(json!)
                    
                } else if data.length == 0 && error == nil{
                    
                    println("Nothing was downloaded")
                    
                } else if error != nil
                {
                    println("An Error Occurred = \(error)")
                }
            }
        )
    }
    // Get the consumption data and display
    func extract_data (data : NSString!){
        var p : NSError?;
        let jsonData:NSData = data.dataUsingEncoding(NSASCIIStringEncoding)!
        let json = JSON(data: jsonData, options: nil, error: &p)
        if p != nil {
            println("Json parsing Error");
        }
        else {
            let AvgCost = json["AvgCost"].stringValue
            let AvgCons = json["AvgCons"].stringValue
            let TotalCons = json["TotalCons"].stringValue
            let progress = json [ "progress"].intValue
            let diff = json["diff"].intValue
            
            
            AvgCost_label.text = " \(AvgCost) LE";
            AvgCons_label.text = " \(AvgCons) KW";
            TotalCons_label.text = " \(TotalCons) KW";
            
            if(progress > 0){
                Progress_label.text = " \(progress) %better / \(diff) KW saved "
            }
            else{
                Progress_label.text = " \(progress) %worse / \(diff) KW wasted "
                
            }
        }
    }
}


/*
// MARK: - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
// Get the new view controller using segue.destinationViewController.
// Pass the selected object to the new view controller.
}
*/