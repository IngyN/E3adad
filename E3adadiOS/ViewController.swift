//
//  ViewController.swift
//  E3adadiOS
//
//  Created by Ingy on 7/12/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import UIKit
//import SwiftyJSON

// class CustomTableViewCell: UITableViewCell{
    
class ViewController: UIViewController, UITableViewDataSource,UITableViewDelegate {
    @IBOutlet weak var tableView: UITableView!
   // var items: [String] = ["","E-3adad", "is", "an" , "awesome","application"]
    var items = [String]()
    var api_url = "http://baseetta.com/hatem/e3adad/history.php?user_id=47"

    override func viewDidLoad() {
        super.viewDidLoad()
        get_data(api_url);
        self.items.append("Hello People")
        PrintAll()
        // Do any additional setup after loading the view, typically from a nib.
        self.tableView.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
    }

    func get_data (url:String)
    {
        let httpMethod = "GET"
        let timeout = 15
        let url = NSURL(string: url)
        let urlRequest = NSMutableURLRequest(URL: url!,
        cachePolicy: .ReloadIgnoringLocalAndRemoteCacheData,
        timeoutInterval: 15.0)
        let queue = NSOperationQueue()

        NSURLConnection.sendAsynchronousRequest(
                                      urlRequest,
                                      queue: queue,
                                      completionHandler: {(response: NSURLResponse!, data: NSData!, error: NSError!) in
                 if data.length > 0 && error == nil{

                     let json = NSString(data: data, encoding: NSASCIIStringEncoding)
                     self.extract_json(json!)
                     self.refresh_table();
//                    println("anything!!!!");
                    self.items.append("Ay shit tayeb?");

                 } else if data.length == 0 && error == nil{

                     println("Nothing was downloaded")

                 } else if error != nil
                 {
                     println("Error happened = \(error)")
                 }
             }
         )
    }

    func refresh_table(){

     dispatch_async(dispatch_get_main_queue(), {
             self.tableView.reloadData()
            return
         })
    }

    func extract_json (data : NSString!)
    {
            let json = JSON(data)
            let Submissions = json["results"]
            for (index, object) in Submissions {
                    let submission_id: String = object["submission_id"].stringValue
                    let reading: String = object["reading"].stringValue
                    let submission_date: String = object["submission_date"].stringValue
                    let is_paid: String = object["is_paid"].stringValue
                    self.items.append(reading)
                    self.items.append(submission_date)
                    self.items.append(is_paid)
                    println("submission_id = \(submission_id) ")
                    println("reading = \(reading) ")
                    println("submission_date = \(submission_date)) ")
                    println("is_paid = \(is_paid) ")
                    self.items.append("Hello People")
                }
    }
  //  func getData(url: String , parameters: [String:AnyObject]){
    func getData(){
        let myUrl = NSURL(string: "http://baseetta.com/hatem/e3adad/history.php?user_id=47")
        let request = NSMutableURLRequest(URL:myUrl!);
        request.HTTPMethod = "GET";
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
        data, response, error in
        
        if error != nil{
            println("error=\(error)")
            return
        }
        //Print response
        println("response = \(response)")
        //print response body
        let responseString = NSString(data: data, encoding: NSUTF8StringEncoding)
        println("responseString = \(responseString)")
        //NSLog("Response ==> %@", responseString!);
        println("Hello Swift")
        dispatch_async(dispatch_get_main_queue()) {
            
        let json = JSON(data: data)
        let Submissions = json["results"]
        for (index, object) in Submissions {
                let submission_id: String = object["submission_id"].stringValue
                let reading: String = object["reading"].stringValue
                let submission_date: String = object["submission_date"].stringValue
                let is_paid: String = object["is_paid"].stringValue
                self.items.append(reading)
                self.items.append(submission_date)
                self.items.append(is_paid)
                println("submission_id = \(submission_id) ")
                println("reading = \(reading) ")
                println("submission_date = \(submission_date)) ")
                println("is_paid = \(is_paid) ")
                self.items.append("Hello People")
            }
          }
        }
        // self.items.append("Hello People")
        task.resume();
    }
    func connection(connection: NSURLConnection!, didReceiveResponse response: NSURLResponse!){
        NSLog("didReceiveResponse")
    }
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.items.count;
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell:UITableViewCell = self.tableView.dequeueReusableCellWithIdentifier("cell") as UITableViewCell
        cell.textLabel?.text = self.items[indexPath.row]
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println("You selected cell #\(indexPath.row)!")
    }
    func PrintAll(){
        //let t = items.count
        for i in items{
            println(i)
        }
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

