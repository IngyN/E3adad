//
//  Home.swift
//  E3adadiOS
//
//  Created by Tommy on 7/17/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import UIKit

class Home: UIViewController {

    @IBAction func signout_tapped(sender: UIButton) {
        
        let appDomain = NSBundle.mainBundle().bundleIdentifier
        NSUserDefaults.standardUserDefaults().removePersistentDomainForName(appDomain!)
        
        self.dismissViewControllerAnimated(true, completion: nil)
        
        
    }
    
}
