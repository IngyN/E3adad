//
//  ViewController.swift
//  E3adadiOS
//
//  Created by Ingy on 7/12/15.
//  Copyright (c) 2015 Ingy. All rights reserved.

// History screen
import UIKit
import Foundation

// class CustomTableViewCell: UITableViewCell{
    
class ViewController: UIViewController, UITableViewDataSource,UITableViewDelegate {
    @IBOutlet weak var tableView: UITableView!
   // var items: [String] = ["","E-3adad", "is", "an" , "awesome","application"]
    var items = [String]()
    var colors = [Int]()
    var api_url = "http://baseetta.com/hatem/e3adad/history.php?user_id=47"

    override func viewDidLoad() {
        super.viewDidLoad()
        get_data(api_url);
        // Do any additional setup after loading the view, typically from a nib.
     self.tableView.registerClass(UITableViewCell.self,forCellReuseIdentifier: "cell")
    /*var nib = UINib(nibName: "customcellTableViewCell", bundle:nil)
     tableView.registerNib(nib, forCellReuseIdentifier: "cell")
        println("hello swifty5")*/
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
//                    self.items.append("Ay shit tayeb?");

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
        
        var p : NSError?;
         let jsonData:NSData = data.dataUsingEncoding(NSASCIIStringEncoding)!
            let json = JSON(data: jsonData, options: nil, error: &p)
        if p != nil {
            println("Json parse Error");
        }
        else {
            let Submissions = json["results"]
            for (index, object) in Submissions {
                    let submission_id: String = object["submission_id"].stringValue
                    let reading: String = object["reading"].stringValue
                    let submission_date: String = object["submission_date"].stringValue
                    let is_paid: Int = object["is_paid"].intValue
                    let month = submission_date.substringWithRange(Range<String.Index>(start: advance(submission_date.startIndex, 5), end: advance(submission_date.startIndex, 7))) //"llo, playgroun"
                
                    let t = "\(getMonth(month))    \(submission_date)    \(reading)KW"
                    self.items.append(t)
                    self.colors.append(is_paid)
                    println("submission_id = \(submission_id) ")
                    println("reading = \(reading) ")
                    println("submission_date = \(submission_date)) ")
                    println("is_paid = \(is_paid) ")
                    println("hello swifty4")
                }
        }
    }
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
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.items.count;
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        println("hello swifty")
        var cell:UITableViewCell = self.tableView.dequeueReusableCellWithIdentifier("cell") as UITableViewCell
        cell.textLabel?.text = self.items[indexPath.row]
   /*     if(self.colors[indexPath.row] == 0 ){
            cell.backgroundColor = UIColor.redColor()
        }
        else if(self.colors[indexPath.row] == 1 ){
            cell.backgroundColor = UIColor.grayColor()
        }
        else if(self.colors[indexPath.row] == 2 ){
            cell.backgroundColor = UIColor.greenColor()
        } */
    /*    var cell: customcellTableViewCell = self.tableView.dequeueReusableCellWithIdentifier("cell") as customcellTableViewCell
       println("hello swifty6")
        cell.cellLabel.text = self.items[indexPath.row]
        cell.cellImage.image = UIImage(named: "home")
        println("hello swifty7") */
      /*  if(self.colors[indexPath.row] == 0 ){
            cell.cellImage.image = UIImage (named: "red")
        }
        else if(self.colors[indexPath.row] == 1 ){
            cell.cellImage.image = UIImage(named: "red")
        }
        else if(self.colors[indexPath.row] == 2 ){
            cell.cellImage.image = UIImage(named: "red")
        } */
       // println("hello swifty2")
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println("You selected cell #\(indexPath.row)!")
    }
    func getMonth(dateAll: String)->String{
        var month: String = " "
        if(dateAll=="07"){
            month = "July"
        }
        return month
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

