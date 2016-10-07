using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SEP2.DefinedException
{
    public class UserInfoException : Exception
    {
        public UserInfoException() 
        {
        }

        public UserInfoException(string message)
            : base(message) 
        {
        }
    }
}