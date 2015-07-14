//
//  SignInViewController.swift
//  E3adadiOS
//
//  Created by Tommy on 7/14/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import UIKit



class SignInViewController: UIViewController {

    @IBOutlet weak var email_txt: UITextField!
    @IBOutlet weak var serial_txt: UITextField!
    @IBOutlet weak var national_id_txt: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func SignInTapped(sender: UIButton) {
         var national_id:NSString = national_id_txt.text as NSString
        var serial:NSString = serial_txt.text as NSString
        var email:NSString = email_txt.text as NSString
        
        if ( national_id.isEqualToString("") || serial.isEqualToString("") || email.isEqualToString("") ) {
            
            var alertView:UIAlertView = UIAlertView()
            alertView.title = "Sign in Failed!"
            alertView.message = "Please enter your National ID, Serial Number, and Email Address"
            alertView.delegate = self
            alertView.addButtonWithTitle("OK")
            alertView.show()
        } else {
            
            let request = NSMutableURLRequest(URL: NSURL(string: "http://baseetta.com/hatem/e3adad/login.php")!)
            request.HTTPMethod = "POST"
            
            let postString = "{\"national_id\":\"" + national_id + "\",\"serial\":\"" + serial + "\",\"email\":\"" + email + "\"}"
           

            request.HTTPBody = postString.dataUsingEncoding(NSUTF8StringEncoding)
            let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
                data, response, error in
                
                if error != nil {
                    println("error=\(error)")
                    return
                }
                
                println("response = \(response)")
                
                let responseString = NSString(data: data, encoding: NSUTF8StringEncoding)
                println("responseString = \(responseString)")
                
               
                var string: NSString = "\(responseString)"
                if string.containsString("ERROR") {
                 println("DOESN't WORK")
                    self.err()
                }else{
                    println("WORKS")
                    self.go()
            
                }
                
                
            }
            task.resume()

            
        }
    }
    
    func err()
    {
        var alertView:UIAlertView = UIAlertView()
        alertView.title = "Sign in Failed!"
        alertView.message = "Check your credentials!!"
        alertView.delegate = self
        alertView.addButtonWithTitle("OK")
        alertView.show()
    }
    func go()
    {
        println("func")
        
        self.performSegueWithIdentifier("sign_in", sender: self)
println("func2")
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

