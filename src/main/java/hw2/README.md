# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------
In order for binary search to work, it must be true that the list 
is sorted in some useful and appropriate manner. This is reminiscent 
of the way that data is stored in a simple array, with entries being 
contiguous in memory, as opposed to the data points of a linked list, 
which is connected by the attributes of the elements of the list 
itself. For instance, we might put the students' emails in an array,
sorted alphabetically. 
It is also noteworthy that students' emails are likely to be highly
diverse with no two emails being the same. As such there is no set
"default value" that an email address or other student identifier
like their name might take on. 

The linked list is useful when individual data points are not stored
contiguously in memory as they are in an array. But the data we are
storing here begs to be ordered alphabetically and this is highly
conducive to placement in a single array where they are stored
contiguously. Therefore I do not think a linkedlist implementation
is appropriate. Another consideration is how we would use the binary
search on emails sorted in a linked list. If sorted alphabetically
in a single array, then we could leverage the midpoints of the array.
But in a linkedlist implementation, if the emails were not sorted
alphabetically in the linkedlist, it would be challenging to find 
some other way to make the list amenable to binary search. 
And if we went ahead and sorted them alphabetically, then at that
point we might as well store them in a single array.

The sparse list as discussed earlier is useful when we have some 
frequent or even predominant default value, but this is not the case
when dealing with such variable objects as student identifiers like
emails.

With these facts in mind, I would recommend using a straightforward
ArrayIndexedList. In this data structure, the data values are all 
stored in a private array. This array could have the emails 
sorted alphabetically.

There is one more consideration, and that is that the list might
grow or shrink with time. In that case, having an array of fixed
capacity like the ArrayIndexedList might not be so optimal. In that 
case, I would give some more consideration to the LinkedList because
its length is dynamic and it would always have just as many nodes as
there are student identifiers, simplifying the implementation of the 
length or midpoint attribute used in binary search, instead of for 
instance having to make a new array with the ArrayIndexedList to 
filter out blank spaces left by students who've just left, or 
to accommodate newly joined students.

Since the add/remove methods must keep the data sorted, if we 
are adding/removing frequently, I recommend the linked list 
since that's just a matter of finding the right node and then shifting
the nodes nearby. With the array, by contrast, we have to shift the 
entirety of the array up or down as necessary, or even make a new 
array of the right length to eliminate empty spots.

Thus, depending on the circumstance, I recommend the ArrayIndexedList
or the LinkedList. If the list size is subject to change frequently
enough, I would lean more towards the LinkedList. Otherwise, I would
generally be inclined towards the ArrayIndexedList. If we used a 
LinkedList, I would recommend sorting student emails alphabetically
or using some other workable index system. 
