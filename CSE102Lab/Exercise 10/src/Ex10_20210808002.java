import java.util.*;

public class Ex10_20210808002 {
}

class User{
    private int id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User,Queue<Message>> messages;

    User(String  username,String email){
        this.username=username;
        this.email=email;
        this.id=hashCode();
        this.following=new HashSet<>();
        this.followers=new HashSet<>();
        this.likedPosts=new HashSet<>();
        this.messages=new HashMap<>();
    }

    public void message(User recipient,String content){
        if (!messages.containsKey(recipient)){
            messages.put(recipient,new LinkedList<>());
            recipient.getMessages().put(this,new LinkedList<>());
        }
        Message message=new Message(this,content);
        messages.get(recipient).offer(message);
        recipient.getMessages().get(this).add(message);
        read(recipient);

    }

    public void read(User user){
        if (messages.containsKey(user)){
            Queue<Message> messageQueue=messages.get(user);
            for (Message message:messageQueue){
                System.out.println(message);
            }
        }
    }

    public void follow(User user){
        if (following.contains(user)){
            following.remove(user);
            user.getFollowers().remove(this);
        }else {
            following.add(user);
            user.getFollowers().add(this);
        }
    }

    public void like(Post post){
        if (likedPosts.contains(post)){
            likedPosts.remove(post);
        }
            post.likedBy(this);

    }

    public Post post(String content){
        return new Post(content);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null || getClass()!=obj.getClass())
            return false;

        User user=(User) obj;
        return id==user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public Comment comment(Post post, String content){
        Comment comment=new Comment(content);
        post.commentBy(this,comment);
        return comment;
    }


    public Map<User, Queue<Message>> getMessages() {
        return messages;
    }
    public int getId(){
        return id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }
}

class Message{
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;
    Message(User sender,String content){
        this.sender=sender;
        this.content=content;
        this.seen=false;
        this.dateSent=new Date();
    }

    public String read(User reader){
        if (!(sender ==reader)){
            seen=true;
        }
        System.out.println( "Sent at: "+dateSent);
        return content;
    }
    public boolean hasRead(){
        if (seen){
            return true;
        }else
            return false;
    }


}

class Post{
    private Date datePosted;
    private String content;
    private Set<User> likes;
    private Map<User,List<Comment>>comments;
    Post(String content){
        this.content=content;
        this.datePosted=new Date();
        this.likes=new HashSet<>();
        this.comments=new HashMap<>();
    }

    public boolean likedBy(User user){
        if (likes.contains(user)){
            likes.remove(user);
            return false;
        }else {
            likes.add(user);
            return true;
        }
    }
    public boolean commentBy(User user,Comment comment){
        if (comments.containsKey(user)){
        }else {
            List<Comment> comments1=new ArrayList<>();
            comments1.add(comment);
            comments.put(user,comments1);
        }
        return true;
    }
    public String getContent(){
        System.out.println("Posted at: "+datePosted);
                return content;
    }
    public Comment getComment(User user,int index){
        if (comments.containsKey(user)){
            List<Comment> comments1=comments.get(user);
            if (index>=0 && index<comments1.size()){
                return comments1.get(index);
            }
        }
        return null;
    }
    public int getCommentCount(){
        int total=0;
        for (List<Comment>comments1: comments.values()){
            total+=comments1.size();
        }
        return total;
    }
    public int getCommentCountByUser(User user){
        if (comments.containsKey(user))
            return comments.get(user).size();

        return 0;
    }
}

class Comment extends Post{

    Comment(String content) {
        super(content);
    }
}

class SocialNetwork{
        private static Map<User,List<Post>> postsByUsers;
        public static User register(String username,String email){
            User user=new User(username,email);
            if (!postsByUsers.containsKey(user)){
                postsByUsers.put(user,new ArrayList<>());
                return user;
            }
            return null;
        }
        public static Post post(User user,String content){
            if (postsByUsers.containsKey(user)){
                Post post=new Post(content);
                List<Post>posts=postsByUsers.get(user);
                posts.add(post);
                return post;
            }
            return null;
        }

        public static User getUser(String email){
            int hashemail=Objects.hash(email);
            for (User user:postsByUsers.keySet()){
                if (user.getId()==hashemail){
                    return user;
                }
            }
            return null;
        }
        public static Set<Post> getFeed(User user){
            Set<Post> feed=new HashSet<>();
            for (User followed:user.getFollowing()){
                if (postsByUsers.containsKey(followed)){
                    feed.addAll(postsByUsers.get(followed));
                }
            }
            return feed;
        }
        public static Map<User,String> search(String keyword){
            Map<User,String>res=new HashMap<>();
            for (User user:postsByUsers.keySet()){
                if (user.getUsername().contains(keyword)){
                    res.put(user,user.getUsername());
                }
            }
            return res;
        }
        public static <K,V> Map<V,Set<K>> reverseMap(Map<K,V>map){
            Map<V,Set<K>>reverse=new HashMap<>();
            for (Map.Entry<K,V> entry:map.entrySet()){
                K key=entry.getKey();
                V value=entry.getValue();
                if (reverse.containsKey(value)){
                    reverse.get(value).add(key);
                }else {
                    Set<K> keys=new HashSet<>();
                    keys.add(key);
                    reverse.put(value,keys);
                }
            }
            return reverse;
        }

}