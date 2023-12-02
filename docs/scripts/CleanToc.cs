/**
 * Author: Ferdinand Su
 * Date: December 2nd, 2023
 * Description: 
 *      This is a workround. 
 *      The docfx `toc.yml` generated `googleapis/docfx-doclet-1.9.0` has bad enties, 
 *      so we have to manually fix it.
 * Usage: Run at root directory, `dotnet script .\docs\scripts\CleanToc.cs`
 */

using System;
using System.IO;
var file = "docs/api/toc.yml";
if (!File.Exists(file))
{
    System.Console.WriteLine("File does not exist!");
    return;
}
var lines = File.ReadAllLines(file);
var output = new List<string>();
foreach (var line in lines)
{
    if (line.StartsWith("- name:") ||
    line == "  items:" ||
    line.Contains("- heading:") ||
    line.EndsWith("name: \"Version history\"") ||
    line.EndsWith("href: \"history.md\"")) continue;
    else if (line.StartsWith("  ")) output.Add(line[2..]);
    else output.Add(line);
}
File.WriteAllLines(file, output);

System.Console.WriteLine("Cleaned Successfully.");