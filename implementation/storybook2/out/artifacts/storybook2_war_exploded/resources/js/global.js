/**
 * Created by Zhipe on 2016-03-17.
 */

// deal with the conflict between jquery and other libraries (eg: angular) with global variable "$"
// after this, need to use jQuery instead of "$" for jquery's function
// just in case some unexpected/unpredictable bugs
jQuery.noConflict();

//
// global variables, should use them very carefully!!!
//
var global_stories       = [],
    global_story         = {},
    global_summary       = {},
    global_summaryList   = [],
    global_character     = {},
    global_characterList = [],
    global_dialog        = {},
    global_dialogList    = [],
    global_scene         = [],
    global_sceneList     = [];

