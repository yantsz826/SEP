//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace MvcApplication1.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    
    public partial class Profile
    {
        [Required(ErrorMessage = "Please provide your Vendor ID. ", AllowEmptyStrings = false)]
        public int VendorID { get; set; }

        [Required(ErrorMessage = "Please provide your Password. ", AllowEmptyStrings = false)]
        [DataType(System.ComponentModel.DataAnnotations.DataType.Password)]
        public string Password { get; set; }

        [Compare("Password", ErrorMessage = "Re-Password does not match. ")]
        [DataType(System.ComponentModel.DataAnnotations.DataType.Password)]
        public string RePassword { get; set; }

        [RegularExpression(@"^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$", ErrorMessage = "Please provide valid email. ")]
        public string Email { get; set; }

        public Nullable<System.DateTime> AvaDate { get; set; }
    }
}
