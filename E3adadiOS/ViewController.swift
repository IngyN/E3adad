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
    var items:Array< String > = Array < String >()
    override func viewDidLoad() {
        super.viewDidLoad()
        getData()
        // Do any additional setup after loading the view, typically from a nib.
        self.tableView.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
            }
  //  func getData(url: String , parameters: [String:AnyObject]){
    func getData(){
        let myUrl = NSURL(string: "http://baseetta.com/hatem/e3adad/history.php?=47")
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
        let json = JSON(data: data)
        let Submissions = json["results"]
        for (index, object) in Submissions {
            let submission_id: String = object["submission_id"].stringValue
            let reading: String = object["reading"].stringValue
            let submission_date: String = object["submission_date"].stringValue
            let is_paid: String = object["is_paid"].stringValue
            self.items.append(submission_id )
            println("submission_id = \(submission_id) ")
            println("reading = \(reading) ")
            println("submission_date = \(submission_date)) ")
            println("is_paid = \(is_paid) ")
        }
        }
        task.resume();
    }
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.items.count;
    }
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell:UITableViewCell = self.tableView.dequeueReusableCellWithIdentifier("cell") as UITableViewCell
        cell.textLabel?.text = self.items[indexPath.row]
        return cell

    }
    /*
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println("You selected cell #\(indexPath.row)!")
        
    }*/

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}
//Parse the JSON Object
/*    let json = JSON(data:data)
let Submissions = json["results"]
for obj in Submissions
{
let submission_id = obj["submission_id"].stringValue
let reading = obj["reading"].stringValue
let submission_date = obj["submission_date"].stringValue
let is_paid = obj["is_paid"].stringValue
//println( "id: \(id) name: \(name)" )
}
//let reading = obj[0]["reading"].string {
}
for (index: String, subJson: JSON) in json {
//Do something you want
}*/
/*
s.setId(String.valueOf(obj.getInt("submission_id")));
s.setReading(String.valueOf(obj.getInt("reading")));
s.setSubmission_date(String.valueOf(obj.get("submission_date")));
s.setIs_paid(obj.getInt("is_paid"));
s.setPayment_date(obj.getString("submission_date"));

+ " /" + reading+ "/" + submission_date + "/" + is_paid)
*/


/*   var err: NSError?
var myJSON = NSJSONSerialization.JSONObjectWithData(data, options: .MutableLeaves, error:&err) as? NSDictionary
if let parseJSON = myJSON {
var firstNameValue = parseJSON["results"]; anyObject?
//  var first = firstNameValue["reading"] as? String
println("firstNameValue: \(first)")
}*/
