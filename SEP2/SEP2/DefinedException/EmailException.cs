using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SEP2.DefinedException
{
    public class EmailException: Exception
    {
        public EmailException() 
        { 
        }

        public EmailException(string message)
            : base(message) 
        {
        }
    }
}