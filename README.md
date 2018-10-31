# DbExporterHelper
#### This library Helps you to Export Db to sdcard or also import form it and also share db to csv to sdcard.

# Images:
![alt Setting IMages1](https://bit.ly/2yKkEfJ)

# Prerequisites
- Android 18.
- Permission for excess of external storage.

[![](https://jitpack.io/v/prabhat1707/DbExporterHelper.svg)](https://jitpack.io/#prabhat1707/DbExporterHelper)


# Installing
## Step 1:- Add it in your root build.gradle at the end of repositories:
````
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
````
## Step 1:- Add the dependency:
````

		dependencies {
	         implementation 'com.github.prabhat1707:DbExporterHelper:v1.0'
	}
	
  
````
#### Option 1:- Export db to external directory

First check the storage Permission then initilize it.

````
 ExportDbUtil exportDbUtil = new ExportDbUtil(this, db name, directory_name, this);
````

````
 /*
 db_path = "/data/com.example.android.databaseexpoter/databases/"
 */
  exportDbUtil.exportDb(db_path);
````

#### Option 2: Export all table to Csv

````
 csv_filename = "test.csv"
 exportDbUtil.exportAllTables(csv_filename);
 
````

#### Option 3: Export single table to csv

````
 exportDbUtil.exportSingleTable(first_table_name,csv_filename);
   
````

### Option 4:Import db from external storage to internal 

````
/*
 db_path = "/data/com.example.android.databaseexpoter/databases/"
 */
 
 if (exportDbUtil.isBackupExist(directory_name)) {
            exportDbUtil.importDBFile(db_path);
        } else {
            Toast.makeText(this, "no Backup", Toast.LENGTH_SHORT).show();

        }
   
````

#### CallBack Method are optional,  "implements ExporterListener"

````

@Override
    public void success(@NotNull String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(@NotNull String message, @NotNull String exception) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
````

#### For Any Query Mail me to "prabhat.rai1707@gmail.com"
#### Thanks 

# License

````
Copyright (c) delight.im <prabhat.rai1707@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable la
w or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

