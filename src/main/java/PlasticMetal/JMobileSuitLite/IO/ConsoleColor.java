package PlasticMetal.JMobileSuitLite.IO;

public enum ConsoleColor
{
    White
            {
                @Override
                public String toString()
                {
                    return "\033[37m";
                }
            },
    Red
            {
                @Override
                public String toString()
                {
                    return "\033[31m";
                }
            },
    DarkRed
            {
                @Override
                public String toString()
                {
                    return "\033[1;31m";
                }
            },
    Cyan
            {
                @Override
                public String toString()
                {
                    return "\033[1;36m";
                }
            },
    DarkCyan
            {
                @Override
                public String toString()
                {
                    return "\033[36m";
                }
            },
    Magenta
            {
                @Override
                public String toString()
                {
                    return "\033[35m";
                }
            },
    DarkMagenta
            {
                @Override
                public String toString()
                {
                    return "\033[1;35m";
                }
            },
    Blue
            {
                @Override
                public String toString()
                {
                    return "\033[1;34m";
                }
            },
    DarkBlue
            {
                @Override
                public String toString()
                {
                    return "\033[34m";
                }
            },
    Green
            {
                @Override
                public String toString()
                {
                    return "\033[32m";
                }
            },
    DarkGreen
            {
                @Override
                public String toString()
                {
                    return "\033[1;32m";
                }
            },
    Yellow
            {
                @Override
                public String toString()
                {
                    return "\033[33m";
                }
            },
    DarkYellow
            {
                @Override
                public String toString()
                {
                    return "\033[1;33m";
                }
            },
    Null{
        @Override
        public String toString()
        {
            return "";
        }
    }
}
