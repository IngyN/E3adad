//
//  HistoryController.swift
//  E3adadiOS
//
//  Created by Ingy on 7/16/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import UIKit

class HistoryController: UITableViewController, UITableViewDelegate, UITableViewDataSource {
    
    var subs : [Submission] = []
    var api_url = "http://baseetta.com/hatem/e3adad/history.php?user_id=47"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        get_data(api_url);
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
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
                    self.subs = self.subs.reverse();
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
    
    func connection(connection: NSURLConnection!, didReceiveResponse response: NSURLResponse!){
        NSLog("didReceiveResponse")
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
                let payment_date: String = object["payment_date"].stringValue
                let is_paid: Int = object["is_paid"].intValue
                let priceS : String = object ["price"].stringValue
                let price : Double = (priceS as NSString).doubleValue;
                
                // Should get Device and user id from Shared Prefs!!!! 
                
                let s : status = status (x: is_paid);
                
                var isub : Submission = Submission ( id: submission_id, userid: "47", deviceid: "0", read: reading, price: price, sub_date: submission_date, pay_date: payment_date, is_paid: s)
                
                subs.append(isub);
            }
        }
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Potentially incomplete method implementation.
        // Return the number of sections.
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete method implementation.
        // Return the number of rows in the section.
        return subs.count
    }

    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath) as UITableViewCell

        let sub = subs [indexPath.row];
        cell.textLabel?.text = sub.getLabel();
        cell.detailTextLabel?.text = sub.getPrice();
        
        var image : UIImage;
        
        if sub.is_paid == .late {
            image = UIImage(named: "status_late")!
        }
        else if(sub.is_paid == .pending)
        {
            image = UIImage(named: "status_pending")!
        }
        else
        {
            image = UIImage(named: "status_paid")!
        }
        
        var imageView = UIImageView(frame: CGRectMake(15, 5, 30, 30)); // set as you want
        
        imageView.image = image;
        
        cell.addSubview(imageView);
        
        return cell
    }


    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return NO if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return NO if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using [segue destinationViewController].
        // Pass the selected object to the new view controller.
    }
    */
    
    

}
