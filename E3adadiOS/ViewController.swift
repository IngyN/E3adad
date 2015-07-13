//
//  ViewController.swift
//  E3adadiOS
//
//  Created by Ingy on 7/12/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import UIKit
// class CustomTableViewCell: UITableViewCell{
    
class ViewController: UIViewController, UITableViewDataSource,UITableViewDelegate {
    @IBOutlet weak var tableView: UITableView!
   // var items: [String] = ["","E-3adad", "is", "an" , "awesome","application"]
    var items = [String]()
    override func viewDidLoad() {
        super.viewDidLoad()
        getData()
        self.items.append("Hello People")
        PrintAll()
        // Do any additional setup after loading the view, typically from a nib.
        self.tableView.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
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

