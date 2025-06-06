/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


define([
  "common-ui/jquery-pentaho-i18n",
  "common-ui/jquery"
], function () {

  var local = {

    init: function () {

      // retrieve i18n map
      var that = this; // trap this

      // initialize buttons definitions
      that.buttons = [
        {
          id: "openButton",
          text: this.i18n.prop('contextAction_open'),
          handler: $.proxy(that.openButtonHandler, that)
        },
        {id: "separator"},
        {
          id: "cutButton",
          text: this.i18n.prop('contextAction_cut'),
          handler: $.proxy(that.cutHandler, that)
        },
        {
          id: "copyButton",
          text: this.i18n.prop('contextAction_copy'),
          handler: $.proxy(that.copyHandler, that)
        },
        {
          id: "deleteButton",
          text: this.i18n.prop('contextAction_delete'),
          handler: $.proxy(that.deleteHandler, that)
        }
      ];

      that.initEventHandlers();
    },

    buttons: [],

    initEventHandlers: function () {
      // listen for file action events
      if (window.parent.mantle_addHandler != undefined)
        window.parent.mantle_addHandler("SolutionFolderActionEvent", this.eventLogger);
    },

    openButtonHandler: function (path) {
      for(var i=0;i<path.length;i++){
        window.parent.mantle_openRepositoryFile(path[i], "RUN");
      }
    },

    cutHandler: function (path, title, id) {
      window.parent.executeCommand("CutFilesCommand", this.buildParameter(path, title, id));
    },

    copyHandler: function (path, title, id) {
      window.parent.executeCommand("CopyFilesCommand", this.buildParameter(path, title, id));
    },

    deleteHandler: function (path, title, id) {
      window.parent.executeCommand("DeleteFileCommand", this.buildParameter(path, title, id));
    },

      buildParameter: function (path, title, id) {
          for (var i=0;i<path.length;i++){
              var tmpPath=path[i];
              var tmpTitle=title[i];
              var tmpId=id[i];
              path[i] = (tmpPath == null ? "/" : tmpPath );
              title[i] = (tmpTitle == null ? "" : tmpTitle );
              id[i] = (tmpId == null ? "" : tmpId );
          }
          var tabbedPaths=path.join("\n");
          var tabbedTitles=title.join("\n");
          var tabbedIds=id.join("\n");

          var retObj= {
              solutionPath: tabbedPaths,
              fileNames: tabbedTitles,
              fileIds: tabbedIds
          };
          return retObj;
     },

    urlParam: function (paramName) {
      var value = new RegExp('[\\?&]' + paramName + '=([^&#]*)').exec(window.parent.location.href);
      if (value) {
        return value[1];
      }
      else {
        return null;
      }
    },

    eventLogger: function (event) {
      console.log(event.action + " : " + event.message);
    },

    enableButtons: function (enableButtons) {
      this.buttons.forEach((buttonDef) => {
        if (buttonDef.id !== "openButton") {
          let target;
          if (buttonDef.id == "separator") {
            target = $(".separator");
          } else {
            target = $("#" + buttonDef.id);
          }

          target.prop("disabled", !enableButtons);

          if (!enableButtons) {
            target.hide();
          } else {
            target.show();
          }
        }
      });
    }

  };

  var MultiSelectButtons = function (i18n) {
    this.i18n = i18n;
    this.init();
  }
  MultiSelectButtons.prototype = local;
  return MultiSelectButtons;
});
