//
//  customcellTableViewCell.swift
//  E3adadiOS
//
//  Created by mohamed rady on 7/14/15.
//  Copyright (c) 2015 Ingy. All rights reserved.
//

import UIKit

class customcellTableViewCell: UITableViewCell {

    @IBOutlet weak var cellImage: UIImageView!
    @IBOutlet weak var cellLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        println("hello swifty3")
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
