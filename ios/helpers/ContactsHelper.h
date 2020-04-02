#import <Foundation/Foundation.h>
#import <Contacts/Contacts.h>

@interface ContactsHelper : NSObject
- (NSDictionary *) contactToDictionary:(CNContact *)person withThumbnails:(BOOL)withThumbnails API_AVAILABLE(ios(9.0));
- (NSString *) getPathForDirectory:(int)directory;
- (NSString *) thumbnailFilePath:(NSString *)recordID;
- (NSString *) getFilePathForThumbnailImage:(CNContact *)contact recordID:(NSString *)recordID API_AVAILABLE(ios(9.0));
@end
